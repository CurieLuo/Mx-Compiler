package IR.Entity;

import IR.Type.IRType;
import Util.Builtins;

public class IRNullConst extends IRConst {

    public IRNullConst() {
        super(Builtins.irNullType);
    }

    public IRNullConst(IRType type) {
        super(type);
    }

    @Override
    public String toString() {
        return "null";
    }
}
