package MIR;

import MIR.Entity.IRGlobalVar;
import MIR.Entity.IRStringConst;
import MIR.Inst.IRRetInst;
import MIR.Type.IRStructType;
import MIR.Type.IRType;
import Util.Builtins;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;

import static Util.Builtins.*;

public class Program {
    public LinkedList<Function> funcs = new LinkedList<>();
    public ArrayList<IRGlobalVar> globalVars = new ArrayList<>();
    public ArrayList<IRStructType> structs = new ArrayList<>();

    public HashMap<String, IRStringConst> stringConsts = new HashMap<>();

    public Function initFunc = new Function("global.var.init", Builtins.irVoidType, false), mainFunc = null;

    public Program() {
        funcs.add(initFunc); //TODO add initFunc (to IRBuilder.funcs)?
        initFunc.returnBlock().addInst(new IRRetInst(irVoid));
    }

    public Function addFunc(String name, IRType type, boolean isBuiltin) {
        Function func = new Function(name, type, isBuiltin);
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

    @Override
    public String toString() {
        String ret = """
                target datalayout = "e-m:e-p270:32:32-p271:32:32-p272:64:64-i64:64-f80:128-n8:16:32:64-S128"
                target triple = "x86_64-pc-linux-gnu"
                """;

        ret += "\n; --------------------structs--------------------\n";
        for (var struct : structs) {
            ret += struct.toDefFormat() + "\n";
        }

        ret += "\n; --------------------string constants--------------------\n";
        for (var str : stringConsts.values()) {
            ret += "%s = private unnamed_addr constant [%d x i8] c\"%s\"\n".formatted(str, str.val.length() + 1, str.escaped());
        }

        ret += "\n; --------------------global variables--------------------\n";
        for (var globalVar : globalVars) {
            ret += "%s = dso_local global %s\n".formatted(globalVar, globalVar.initVal.toTypedFormat());
        }

        ret += " \n; --------------------functions--------------------\n";
        for (var func : funcs) ret += func + "\n";

//        ret += " \n; --------------------isBuiltin functions--------------------\n";
        String[] fmt = new String[4];
        String argFmt = "";
        for (int i = 0; i < 4; i++) {
            fmt[i] = "declare dso_local %s @%s(" + argFmt + ")\n";
            if (i != 0) argFmt += ", ";
            argFmt += "%s";
        }
        ret += fmt[1].formatted(irStringType, "malloc", irIntType);
        ret += fmt[2].formatted(irStringType, "string.add", irStringType, irStringType);
        String[] ops = {"lt", "gt", "le", "ge", "eq", "ne"};
        for (String op : ops)
            ret += fmt[2].formatted(irBoolType, "string." + op, irStringType, irStringType);

        return ret;
    }
}
