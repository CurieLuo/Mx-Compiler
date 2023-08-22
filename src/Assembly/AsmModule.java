package Assembly;

import Assembly.Operand.GlobalValue;
import Assembly.Operand.GlobalStringConst;

import java.util.ArrayList;

public class AsmModule {
    public ArrayList<GlobalStringConst> stringConsts = new ArrayList<>();
    public ArrayList<GlobalValue> globalValues = new ArrayList<>();
    public ArrayList<AsmFunction> funcs = new ArrayList<>();

    public GlobalValue addValue(GlobalValue val) {
        globalValues.add(val);
        return val;
    }

    public GlobalStringConst addString(GlobalStringConst str) {
        stringConsts.add(str);
        return str;
    }

    public AsmFunction addFunc(AsmFunction func) {
        funcs.add(func);
        return func;
    }

    @Override
    public String toString() {
        String ret = "  .text\n";
        for (var func : funcs) {
            ret += func + "\n";
        }
        if (!globalValues.isEmpty()) {
            ret += "\n  .data\n";
            for (var symbol : globalValues) {
                ret += symbol;
            }
        }
        if (!stringConsts.isEmpty()) {
            ret += "\n  .rodata\n";
            for (var symbol : stringConsts) {
                ret += symbol;
            }
        }
        return ret;
    }
}
