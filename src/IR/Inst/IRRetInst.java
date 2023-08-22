package IR.Inst;

import IR.Entity.Entity;
import IR.IRVisitor;

public class IRRetInst extends IRTerminatorInst {
    public Entity val;

    public IRRetInst(Entity val) {
        this.val = val;
    }

    @Override
    public void accept(IRVisitor visitor) {
        visitor.visit(this);
    }

    @Override
    public String toString() {
        return "ret %s".formatted(val.toTypedFormat());
    }
}
