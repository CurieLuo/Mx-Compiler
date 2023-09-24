package IR.Inst;

import IR.IRBasicBlock;
import IR.Entity.IREntity;
import IR.IRVisitor;

import java.util.HashSet;

public class IRBranchInst extends IRTerminatorInst {
    public IREntity condition;
    public IRBasicBlock trueBlock, falseBlock;

    public IRBranchInst(IREntity condition, IRBasicBlock trueBlock, IRBasicBlock falseBlock) {
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

    @Override
    public HashSet<IREntity> getUse() {
        HashSet<IREntity> ret = new HashSet<>();
        ret.add(condition);
        return ret;
    }
}
