package IR.Inst;

import IR.IRBasicBlock;
import IR.Entity.Entity;
import IR.IRVisitor;

public class IRBranchInst extends IRTerminatorInst {
    public Entity condition;
    public IRBasicBlock trueBlock, falseBlock;

    public IRBranchInst(Entity condition, IRBasicBlock trueBlock, IRBasicBlock falseBlock) {
        this.condition = condition;
        this.trueBlock = trueBlock;
        this.falseBlock = falseBlock;
    }

    @Override
    public void accept(IRVisitor visitor) {
        visitor.visit(this);
    }

    @Override
    public String toString() {
        return "br i1 %s, label %%%s, label %%%s".formatted(condition, trueBlock.label, falseBlock.label);
    }
}
