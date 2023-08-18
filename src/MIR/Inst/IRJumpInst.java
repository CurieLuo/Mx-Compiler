package MIR.Inst;

import MIR.BasicBlock;

public class IRJumpInst extends IRTerminatorInst {
    public BasicBlock destBlock;

    public IRJumpInst(BasicBlock destBlock) {
        super();
        this.destBlock = destBlock;
    }

    @Override
    public String toString() {
        return "br label %%%s".formatted(destBlock.label);
    }
}
