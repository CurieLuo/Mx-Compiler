package IR.Inst;

import IR.Entity.Entity;
import IR.Entity.IRRegister;
import IR.IRVisitor;
import IR.Type.IRType;

public class IRBinaryInst extends IRInst {
    public IRRegister reg;
    public String op;
    public IRType type;

    public Entity left, right;

    public IRBinaryInst(IRRegister reg, String op, Entity left, Entity right) {
        this.reg = reg;
        this.op = op;
        this.type = left.type;
        this.left = left;
        this.right = right;
    }

    @Override
    public void accept(IRVisitor visitor) {
        visitor.visit(this);
    }

    @Override
    public String toString() {
        return "%s = %s %s %s, %s".formatted(reg, op, type, left, right);
    }
}
