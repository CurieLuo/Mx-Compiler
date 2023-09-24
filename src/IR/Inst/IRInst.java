package IR.Inst;

import IR.Entity.*;
import IR.IRBasicBlock;
import IR.IRVisitor;

import java.util.HashSet;

public abstract class IRInst {
    public IRBasicBlock parentBlock;

    public IRInst() {
    }

    public abstract void accept(IRVisitor visitor);

    @Override
    public abstract String toString(); //debug

    public HashSet<IREntity> getUse() {
        return new HashSet<>();
    }
}
