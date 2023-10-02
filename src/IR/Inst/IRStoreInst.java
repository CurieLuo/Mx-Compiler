package IR.Inst;

import IR.Entity.IREntity;
import IR.Entity.IRRegister;
import IR.IRVisitor;

import java.util.HashSet;

public class IRStoreInst extends IRInst {
    public IREntity val;
    public IRRegister pointer;

    public IRStoreInst(IREntity val, IRRegister pointer) {
        this.val = val;
        this.pointer = pointer;
    }

    @Override
    public void accept(IRVisitor visitor) {
        visitor.visit(this);
    }

    @Override
    public String toString() {
        return "store %s, %s".formatted(val.toTypedFormat(), pointer.toTypedFormat());
    }

    @Override
    public HashSet<IREntity> getUse() {
        HashSet<IREntity> ret = new HashSet<>();
        ret.add(val);
        ret.add(pointer);
        return ret;
    }

    @Override
    public void replaceUse(IREntity val0, IREntity val1) {
        if (val == val0) val = val1;
        if (pointer == val0) pointer = (IRRegister) val1;
    }
}
