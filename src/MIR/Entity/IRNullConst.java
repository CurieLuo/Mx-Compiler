package MIR.Entity;

import MIR.Type.IRArrayType;
import MIR.Type.IRStructType;
import MIR.Type.IRType;
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
