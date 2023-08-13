package MIR.Entity;

import Util.Builtins;

public class IRCondConst extends IRConst {
    public boolean val;

    public IRCondConst(boolean val) {
        super(Builtins.irCondType);
        this.val = val;
    }

    @Override
    public String toString() {
        return val ? "true" : "false";
    }
}
