package IR.Inst;

import IR.Entity.IRRegister;
import IR.IRVisitor;
import IR.Type.IRPtrType;
import IR.Type.IRType;

public class IRAllocaInst extends IRInst {
    public IRRegister reg;

    public IRType type;
    public int paramIndex = -1;

    public IRAllocaInst(IRRegister reg) {
        this.reg = reg;
        this.type = ((IRPtrType) reg.type).pointToType();
    }

    public IRAllocaInst(IRRegister reg, int paramIndex) {
        this(reg);
        this.paramIndex = paramIndex;
    }

    @Override
    public void accept(IRVisitor visitor) {
        visitor.visit(this);
    }

    @Override
    public String toString() {
        return "%s = alloca %s".formatted(reg, type);
    }
}
