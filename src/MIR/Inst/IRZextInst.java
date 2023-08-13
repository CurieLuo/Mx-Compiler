package MIR.Inst;

import MIR.BasicBlock;
import MIR.Entity.Entity;
import MIR.Entity.IRRegister;

public class IRZextInst extends IRInst {
    public IRRegister reg;
    public Entity val;

    public IRZextInst(BasicBlock parentBlock, IRRegister reg, Entity val) {
        super(parentBlock);
        this.reg = reg;
        this.val = val;
    }

    @Override
    public String toString() {
        return "%s = zext %s to %s".formatted(reg, val.toTypedFormat(), reg.type);
    }
}
