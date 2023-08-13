package MIR.Inst;

import MIR.BasicBlock;
import MIR.Entity.Entity;
import MIR.Entity.IRRegister;

public class IRStoreInst extends IRInst {
    public Entity val;
    public Entity pointer;

    public IRStoreInst(BasicBlock parentBlock, IRRegister val, Entity pointer) {
        super(parentBlock);
        this.val = val;
        this.pointer = pointer;
    }

    @Override
    public String toString() {
        return "store %s, %s".formatted(val.toTypedFormat(), pointer.toTypedFormat());
    }
}
