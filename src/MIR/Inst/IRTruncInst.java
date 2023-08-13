package MIR.Inst;

import MIR.BasicBlock;
import MIR.Entity.Entity;
import MIR.Entity.IRRegister;
import MIR.Type.IRPtrType;

public class IRTruncInst extends IRInst {
    public IRRegister reg;
    public Entity val;

    public IRTruncInst(BasicBlock parentBlock, IRRegister reg, Entity val) {
        super(parentBlock);
        this.reg = reg;
        this.val = val;
    }

    @Override
    public String toString() {
        return "%s = trunc %s to %s".formatted(reg, val.toTypedFormat(), reg.type);
    }
}
