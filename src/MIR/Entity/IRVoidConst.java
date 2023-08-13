package MIR.Entity;

import Util.Builtins;

public class IRVoidConst extends IRConst {

    public IRVoidConst() {
        super(Builtins.irVoidType);
    }

    @Override
    public String toString() {
        return "void";
    }

    @Override
    public String toTypedFormat() {
        return "void";
    }
}
