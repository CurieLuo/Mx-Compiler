package MIR.Inst;

import MIR.BasicBlock;

public abstract class IRInst {
    public BasicBlock parentBlock;

    public IRInst(BasicBlock parentBlock) {
        this.parentBlock = parentBlock;
    }

    @Override
    public abstract String toString(); //debug
}
