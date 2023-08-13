package MIR;

import MIR.Entity.IRGlobalVar;
import MIR.Entity.IRStringConst;
import MIR.Type.IRStructType;
import Util.Builtins;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;

import static Util.Builtins.*;

public class Program {
    public LinkedList<Function> funcs = new LinkedList<>();
    public ArrayList<IRGlobalVar> globalVars = new ArrayList<>();
    public ArrayList<IRStructType> structs = new ArrayList<>();

    public HashMap<String, IRStringConst> stringConsts = new HashMap<>();

    public Function initFunc = new Function("__mx_global_var_init", Builtins.irVoidType), mainFunc;

    public Program() {
        //TODO initFunc
    }

    public IRStringConst addStringConst(String val) {
        if (!stringConsts.containsKey(val)) stringConsts.put(val, new IRStringConst(val));
        return stringConsts.get(val);
    }

    @Override
    public String toString() {
        String ret = """
                target datalayout = "e-m:e-p270:32:32-p271:32:32-p272:64:64-i64:64-f80:128-n8:16:32:64-S128"
                target triple = "x86_64-pc-linux-gnu"\n
                """;

        for (var struct : structs) {
            ret += "%s = type { ".formatted(struct);
            for (int i = 0, n = struct.memberTypes.size(); i < n; i++) {
                if (i != 0) ret += ", ";
                ret += struct.memberTypes.get(i);
            }
            ret += " }\n";
        }

        for (var str : stringConsts.values()) {
            ret += "%s = private unnamed_addr constant [%d x i8] c\"%s\"\n".formatted(str, str.val.length() + 1, str.escaped());
        }

        for (var globalVar : globalVars) {
            ret += "%s = dso_local global %s\n".formatted(globalVar, globalVar.initVal.toTypedFormat());
        }

        for (var func : funcs) ret += func + "\n";

        String[] fmt = new String[4];
        String argFmt = "";
        for (int i = 0; i < 4; i++) {
            fmt[i] = "declare dso_local %s @%s(" + argFmt + ")\n";
            if (i != 0) argFmt += ", ";
            argFmt += "%s";
        }
        ret += fmt[1].formatted(irVoidType, "print", irStringType);
        ret += fmt[1].formatted(irVoidType, "println", irStringType);
        ret += fmt[1].formatted(irVoidType, "printInt", irIntType);
        ret += fmt[1].formatted(irVoidType, "printlnInt", irIntType);
        ret += fmt[0].formatted(irIntType, "getInt");
        ret += fmt[0].formatted(irStringType, "getString");
        ret += fmt[1].formatted(irStringType, "toString", irIntType);

        ret += fmt[1].formatted(irIntType, "string::parseInt", irStringType);
        ret += fmt[1].formatted(irIntType, "string::ord", irStringType);
        ret += fmt[1].formatted(irIntType, "string::length", irStringType);
        ret += fmt[3].formatted(irStringType, "string::substring", irStringType, irIntType, irIntType);
        //TODO add "\0"

        ret += fmt[1].formatted(irStringType, "malloc", irIntType);
        //TODO store array size
        ret += fmt[2].formatted(irStringType, "string::add", irStringType, irStringType);
        String[] ops = {"lt", "gt", "le", "ge", "eq", "ne"};
        for (String op : ops)
            ret += fmt[2].formatted(irCondType, "string::" + op, irStringType, irStringType);
        return ret;
    }
}
