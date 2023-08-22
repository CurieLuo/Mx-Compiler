package IR.Entity;

import Util.Builtins;

public class IRBoolConst extends IRConst {
    public boolean val;

    public IRBoolConst(boolean val) {
        super(Builtins.irBoolType);
        this.val = val;
    }

    public static IRBoolConst instance(boolean val) {
        return val ? Builtins.irBoolTrue : Builtins.irBoolFalse;
    }

    @Override
    public String toString() {
        return Boolean.toString(val);
    }
}
