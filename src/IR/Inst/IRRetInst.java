package IR.Inst;

import IR.Entity.IREntity;
import IR.IRVisitor;

import java.util.HashSet;

public class IRRetInst extends IRTerminatorInst {
    public IREntity val;

    public IRRetInst(IREntity val) {
        this.val = val;
    }

    @Override
    public void accept(IRVisitor visitor) {
        visitor.visit(this);
    }

    @Override
    public String toString() {
        return "ret %s".formatted(val.toTypedFormat());
    }

    @Override
    public HashSet<IREntity> getUse() {
        HashSet<IREntity> ret = new HashSet<>();
        ret.add(val);
        return ret;
    }

    @Override
    public void replaceUse(IREntity val0, IREntity val1) {
        if (val == val0) val = val1;
    }
}
