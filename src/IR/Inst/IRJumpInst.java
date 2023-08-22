package IR.Inst;

import IR.IRBasicBlock;
import IR.IRVisitor;

public class IRJumpInst extends IRTerminatorInst {
    public IRBasicBlock destBlock;

    public IRJumpInst(IRBasicBlock destBlock) {
        this.destBlock = destBlock;
    }

    @Override
    public void accept(IRVisitor visitor) {
        visitor.visit(this);
    }

    @Override
    public String toString() {
        return "br label %%%s".formatted(destBlock.label);
    }
}
