package IR.Type;

import IR.Entity.IRConst;
import IR.Entity.IRNullConst;

public class IRPtrType extends IRType {
    public IRType baseType;
    public int dim;

    public IRPtrType(IRType pointToType) {
        super("ptr", 4);
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
        this.baseType = baseType;
        this.dim = dim;
    }

    public IRType pointToType() {
        return dim == 1 ? baseType : new IRPtrType(baseType, dim - 1);
    }

    @Override
    public IRConst defaultValue() {
        return new IRNullConst(this);
    }
}
