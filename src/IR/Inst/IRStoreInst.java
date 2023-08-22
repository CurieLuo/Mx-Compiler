package IR.Inst;

import IR.Entity.Entity;
import IR.IRVisitor;

public class IRStoreInst extends IRInst {
    public Entity val;
    public Entity pointer;

    public IRStoreInst(Entity val, Entity pointer) {
        this.val = val;
        this.pointer = pointer;
    }

    @Override
    public void accept(IRVisitor visitor) {
        visitor.visit(this);
    }

    @Override
    public String toString() {
        return "store %s, %s".formatted(val.toTypedFormat(), pointer.toTypedFormat());
    }
}
