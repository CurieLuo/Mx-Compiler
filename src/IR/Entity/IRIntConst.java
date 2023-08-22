package IR.Entity;

import Util.Builtins;

public class IRIntConst extends IRConst {
    public int val;

    public IRIntConst(int val) {
        super(Builtins.irIntType);
        this.val = val;
    }

    @Override
    public String toString() {
        return Integer.toString(val);
    }
}
