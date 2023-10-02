package IR.Inst;

import IR.Entity.IREntity;
import IR.Entity.IRRegister;
import IR.IRVisitor;
import IR.Type.IRType;

import java.util.HashSet;

public class IRBinaryInst extends IRInst {
    public IRRegister reg;
    public String op;
    public IRType type;

    public IREntity left, right;

    public IRBinaryInst(IRRegister reg, String op, IREntity left, IREntity right) {
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

    @Override
    public HashSet<IREntity> getUse() {
        HashSet<IREntity> ret = new HashSet<>();
        ret.add(left);
        ret.add(right);
        return ret;
    }

    @Override
    public void replaceUse(IREntity val0, IREntity val1) {
        if (left == val0) left = val1;
        if (right == val0) right = val1;
    }
}
