package MIR.Entity;

import Util.Builtins;

public class IRBoolConst extends IRConst {
    public boolean val;

    public IRBoolConst(boolean val) {
        super(Builtins.irBoolType);
        this.val = val;
    }

    @Override
    public String toString() {
//        return val ? "true" : "false";
        return val ? "1" : "0"; //TODO
    }
}
