package IR.Inst;

import IR.Entity.IREntity;
import IR.Entity.IRRegister;
import IR.IRVisitor;

import java.util.HashSet;

public class IRLoadInst extends IRInst {
    public IRRegister reg;
    public IREntity pointer;

    public IRLoadInst(IRRegister reg, IREntity pointer) {
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

    @Override
    public HashSet<IREntity> getUse() {
        HashSet<IREntity> ret = new HashSet<>();
        ret.add(pointer);
        return ret;
    }
}
