package MIR.Inst;

import MIR.BasicBlock;
import MIR.Entity.Entity;

public class IRRetInst extends IRTerminatorInst {
    public Entity val;

    public IRRetInst(Entity val) {
        super();
        this.val = val;
    }

    @Override
    public String toString() {
        return "ret %s".formatted(val.toTypedFormat());
    }
}
