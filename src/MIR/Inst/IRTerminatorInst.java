package MIR.Inst;

import MIR.BasicBlock;

public abstract class IRTerminatorInst extends IRInst {
    public IRTerminatorInst(BasicBlock parentBlock) {
        super(parentBlock);
    }
}
