package MIR.Type;

import MIR.Entity.Entity;
import MIR.Entity.IRNullConst;
import Util.Builtins;

public class IRPtrType extends IRType {
    public IRType baseType;
    public int dim;

    public IRPtrType(IRType pointToType) {
        super("ptr", 4);
//        super(pointToType.name + "*", 4);
        if (pointToType instanceof IRPtrType ptr) {
            baseType = ptr.baseType;
            dim = ptr.dim + 1;
        } else {
            baseType = pointToType;
            dim = 1;
        }
    }

    public IRPtrType(IRType baseType, int dim) {
        super("ptr", 4);
//        super(baseType.name + "*".repeat(dim), 4);
        this.baseType = baseType;
        this.dim = dim;
    }

    public IRType pointToType() {
        return dim == 1 ? baseType : new IRPtrType(baseType, dim - 1);
    }

    @Override
    public Entity defaultValue() {
        return new IRNullConst(this);
    }
}
