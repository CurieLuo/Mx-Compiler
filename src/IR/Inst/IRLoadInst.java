package IR.Inst;

import IR.Entity.Entity;
import IR.Entity.IRRegister;
import IR.IRVisitor;

public class IRLoadInst extends IRInst {
    public IRRegister reg;
    public Entity pointer;

    public IRLoadInst(IRRegister reg, Entity pointer) {
        this.reg = reg;
        this.pointer = pointer;
    }

    @Override
    public void accept(IRVisitor visitor) {
        visitor.visit(this);
    }

    @Override
    public String toString() {
        return "%s = load %s, %s".formatted(reg, reg.type, pointer.toTypedFormat());
    }
}
