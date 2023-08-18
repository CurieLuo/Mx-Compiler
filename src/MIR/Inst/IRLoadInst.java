package MIR.Inst;

import MIR.BasicBlock;
import MIR.Entity.Entity;
import MIR.Entity.IRRegister;

public class IRLoadInst extends IRInst {
    public IRRegister reg;
    public Entity pointer;

    public IRLoadInst(IRRegister reg, Entity pointer) {
        super();
        this.reg = reg;
        this.pointer = pointer;
    }

    @Override
    public String toString() {
        return "%s = load %s, %s".formatted(reg, reg.type, pointer.toTypedFormat());
    }
}
