package IR;

import IR.Entity.IRGlobalVar;
import IR.Entity.IRStringConst;
import IR.Inst.IRRetInst;
import IR.Type.IRStructType;
import IR.Type.IRType;
import Util.Builtins;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;

import static Util.Builtins.*;

public class IRProgram {
    public LinkedList<IRFunction> funcs = new LinkedList<>();
    public ArrayList<IRGlobalVar> globalVars = new ArrayList<>();
    public ArrayList<IRStructType> structs = new ArrayList<>();

    public HashMap<String, IRStringConst> stringConsts = new HashMap<>();

    public IRFunction initFunc = new IRFunction("global.var.init", Builtins.irVoidType, false), mainFunc = null;

    public IRProgram() {
        funcs.add(initFunc);
        initFunc.returnBlock().addInst(new IRRetInst(irVoid));
    }

    public IRFunction addFunc(String name, IRType type, boolean isBuiltin) {
        IRFunction func = new IRFunction(name, type, isBuiltin);
        funcs.add(func);
        if (name.equals("main")) mainFunc = func;
        return func;
    }

    public IRGlobalVar addGlobalVar(String name, IRType type) {
        IRGlobalVar globalVar = new IRGlobalVar(name, type);
        globalVars.add(globalVar);
        return globalVar;
    }

    public IRStructType addStruct(String name) {
        IRStructType struct = new IRStructType(name);
        structs.add(struct);
        return struct;
    }

    public IRStringConst addStringConst(String val) {
        if (!stringConsts.containsKey(val)) stringConsts.put(val, new IRStringConst(val));
        return stringConsts.get(val);
    }

    public void accept(IRVisitor visitor) {
        visitor.visit(this);
    }

    @Override
    public String toString() {
        StringBuilder ret = new StringBuilder("""
                target datalayout = "e-m:e-p270:32:32-p271:32:32-p272:64:64-i64:64-f80:128-n8:16:32:64-S128"
                target triple = "x86_64-pc-linux-gnu"
                """);

        ret.append("\n; --------------------structs--------------------\n");
        for (var struct : structs) {
            ret.append(struct.toDefFormat()).append("\n");
        }

        ret.append("\n; --------------------string constants--------------------\n");
        for (var str : stringConsts.values()) {
            ret.append("%s = private unnamed_addr constant [%d x i8] c\"%s\"\n".formatted(str, str.val.length() + 1, str.escaped()));
        }

        ret.append("\n; --------------------global variables--------------------\n");
        for (var globalVar : globalVars) {
            ret.append("%s = dso_local global %s\n".formatted(globalVar, globalVar.initVal.toTypedFormat()));
        }

        ret.append(" \n; --------------------functions--------------------\n");
        for (var func : funcs) ret.append(func).append("\n");

        String[] fmt = new String[4];
        StringBuilder argFmt = new StringBuilder();
        for (int i = 0; i < 4; i++) {
            fmt[i] = "declare dso_local %s @%s(" + argFmt + ")\n";
            if (i != 0) argFmt.append(", ");
            argFmt.append("%s");
        }
        ret.append(fmt[1].formatted(irStringType, "malloc", irIntType));
        ret.append(fmt[2].formatted(irStringType, "string.add", irStringType, irStringType));
        String[] ops = {"lt", "gt", "le", "ge", "eq", "ne"};
        for (String op : ops)
            ret.append(fmt[2].formatted(irBoolType, "string." + op, irStringType, irStringType));

        return ret.toString();
    }
}
