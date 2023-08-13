package MIR.Inst;

import MIR.BasicBlock;
import MIR.Entity.Entity;

public class IRBranchInst extends IRTerminatorInst {
    public Entity condition;
    public BasicBlock trueBlock, falseBlock;

    public IRBranchInst(BasicBlock parentBlock, Entity condition, BasicBlock trueBlock, BasicBlock falseBlock) {
        super(parentBlock);
        this.condition = condition;
        this.trueBlock = trueBlock;
        this.falseBlock = falseBlock;
    }

    @Override
    public String toString() {
        return "br i1 %s, label %%%s, label %%%s".formatted(condition, trueBlock.label, falseBlock.label);
    }
}
