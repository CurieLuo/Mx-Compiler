package MIR.Type;

import MIR.Entity.Entity;
import Util.Builtins;

public class IRPtrType extends IRType {
    public IRType baseType;
    public int dim; //TODO

    public IRPtrType(IRType pointToType) {
        super("ptr", 4);
//        super(pointToType.name + "*", 4);
        if (pointToType instanceof IRPtrType) {
            baseType = ((IRPtrType) pointToType).baseType;
            dim = ((IRPtrType) pointToType).dim + 1;
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
}
