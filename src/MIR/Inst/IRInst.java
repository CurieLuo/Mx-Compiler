package MIR.Inst;

import MIR.BasicBlock;

public abstract class IRInst {
    public BasicBlock parentBlock;

    public IRInst() {
    }

    @Override
    public abstract String toString(); //debug
}
