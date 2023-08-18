package MIR.Inst;

import MIR.Entity.Entity;

public class IRStoreInst extends IRInst {
    public Entity val;
    public Entity pointer;

    public IRStoreInst(Entity val, Entity pointer) {
        super();
        this.val = val;
        this.pointer = pointer;
    }

    @Override
    public String toString() {
        return "store %s, %s".formatted(val.toTypedFormat(), pointer.toTypedFormat());
    }
}
