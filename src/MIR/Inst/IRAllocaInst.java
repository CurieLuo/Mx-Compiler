package MIR.Inst;

import MIR.Entity.IRRegister;
import MIR.Type.IRPtrType;
import MIR.Type.IRType;

public class IRAllocaInst extends IRInst {
    public IRRegister reg;

    public IRType type;

    public IRAllocaInst(IRRegister reg) {
        super();
        this.reg = reg;
        this.type = ((IRPtrType) reg.type).pointToType();
    }

    @Override
    public String toString() {
        return "%s = alloca %s".formatted(reg, type);
    }
}
