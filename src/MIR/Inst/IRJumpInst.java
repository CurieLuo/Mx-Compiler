package MIR.Inst;

import MIR.BasicBlock;

public class IRJumpInst extends IRTerminatorInst {
    public BasicBlock destBlock;

    public IRJumpInst(BasicBlock parentBlock, BasicBlock destBlock) {
        super(parentBlock);
        this.destBlock = destBlock;
    }

    @Override
    public String toString() {
        return "br label %%%s".formatted(destBlock.label);
    }
}
