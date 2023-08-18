package MIR.Inst;

import MIR.Entity.Entity;
import MIR.Entity.IRRegister;
import MIR.Type.IRType;

public class IRIcmpInst extends IRInst {
    public IRRegister reg;
    public String op;
    public IRType type;

    public Entity left, right;

    public IRIcmpInst(IRRegister reg, String op, Entity left, Entity right) {
        super();
        this.reg = reg;
        this.op = op;
        this.type = left.type;
        this.left = left;
        this.right = right;
    }

    @Override
    public String toString() {
        return "%s = icmp %s %s %s, %s".formatted(reg, op, type, left, right);
    }
}
