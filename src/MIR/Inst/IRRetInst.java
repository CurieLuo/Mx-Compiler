package MIR.Inst;

import MIR.BasicBlock;
import MIR.Entity.Entity;

public class IRRetInst extends IRTerminatorInst {
    public Entity val;

    public IRRetInst(BasicBlock parentBlock, Entity val) {
        super(parentBlock);
        this.val = val;
    }

    @Override
    public String toString() {
        return "ret %s".formatted(val.toTypedFormat());
    }
}
