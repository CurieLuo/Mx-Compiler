package IR.Inst;

import IR.Entity.Entity;
import IR.Entity.IRRegister;
import IR.IRVisitor;
import IR.Type.IRType;

public class IRIcmpInst extends IRInst {
    public IRRegister reg;
    public String op;
    public IRType type;

    public Entity left, right;

    public IRIcmpInst(IRRegister reg, String op, Entity left, Entity right) {
        this.reg = reg;
        this.op = op;
        this.type = left.type;
        this.left = left;
        this.right = right;
    }

    public static String toShortForm(String op) {
        return op.length() == 2 ? op : op.substring(1);
    }

    @Override
    public void accept(IRVisitor visitor) {
        visitor.visit(this);
    }

    @Override
    public String toString() {
        return "%s = icmp %s %s %s, %s".formatted(reg, op, type, left, right);
    }
}
