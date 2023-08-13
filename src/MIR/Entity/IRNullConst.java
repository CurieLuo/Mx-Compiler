package MIR.Entity;

import Util.Builtins;

public class IRNullConst extends IRConst {

    public IRNullConst() {
        super(Builtins.irNullType);
    }

    @Override
    public String toString() {
        return "null";
    }
}
