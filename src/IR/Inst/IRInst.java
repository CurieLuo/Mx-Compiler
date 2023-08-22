package IR.Inst;

import IR.IRBasicBlock;
import IR.IRVisitor;

public abstract class IRInst {
    public IRBasicBlock parentBlock;

    public IRInst() {
    }

    public abstract void accept(IRVisitor visitor);

    @Override
    public abstract String toString(); //debug
}
