package IR.Entity;

import IR.Type.IRType;

public abstract class IRConst extends IREntity {
    public IRConst(IRType type) {
        super(type);
    }

    public int toInt() {
        return this instanceof IRIntConst intVal ?
                intVal.val :
                this instanceof IRBoolConst boolVal ?
                        (boolVal.val ? 1 : 0) : 0;
    }
}
