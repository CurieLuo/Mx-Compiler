package MIR.Inst;

import MIR.BasicBlock;
import MIR.Entity.Entity;
import MIR.Entity.IRRegister;
import MIR.Type.IRType;

public class IRBinaryInst extends IRInst {
    public IRRegister reg;
    public String op; //TODO translate
    public IRType type;

    public Entity left, right;

    public IRBinaryInst(BasicBlock parentBlock, IRRegister reg, String op, Entity left, Entity right) {
        super(parentBlock);
        this.reg = reg;
        this.op = op;
        this.type = left.type;
        this.left = left;
        this.right = right;
    }

    @Override
    public String toString() {
        return "%s = %s %s %s, %s".formatted(reg, op, type, left, right);
    }
}
