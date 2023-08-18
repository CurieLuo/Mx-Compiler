package MIR.Entity;

import MIR.Type.IRType;
import Util.Builtins;

public class IREmptyConst extends IRConst {

    public IREmptyConst() {
        super(Builtins.irNullType);
    }

    public IREmptyConst(IRType type) {
        super(type);
    }

    @Override
    public String toString() {
        return "zeroinitializer";
    }
}
