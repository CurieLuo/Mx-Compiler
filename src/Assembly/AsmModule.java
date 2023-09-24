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
        StringBuilder ret = new StringBuilder("  .text\n");
        for (var func : funcs) {
            ret.append(func).append("\n");
        }
        if (!globalValues.isEmpty()) {
            ret.append("\n  .data\n");
            for (var symbol : globalValues) {
                ret.append(symbol);
            }
        }
        if (!stringConsts.isEmpty()) {
            ret.append("\n  .rodata\n");
            for (var symbol : stringConsts) {
                ret.append(symbol);
            }
        }
        return ret.toString();
    }
}
