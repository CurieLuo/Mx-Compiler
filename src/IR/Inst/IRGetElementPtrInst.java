package IR.Inst;

import IR.Entity.IREntity;
import IR.Entity.IRRegister;
import IR.IRVisitor;
import IR.Type.IRPtrType;
import IR.Type.IRType;

import java.util.HashSet;
import java.util.LinkedList;

public class IRGetElementPtrInst extends IRInst {
    public IRRegister reg;
    public IRType pointToType;
    public IREntity pointer;

    public LinkedList<IREntity> indices = new LinkedList<>();

    public IRGetElementPtrInst(IRRegister reg, IREntity pointer, IREntity... indices) {
        this.reg = reg;
        this.pointer = pointer;
        this.pointToType = ((IRPtrType) pointer.type).pointToType();
        for (var index : indices)
            this.indices.add(index);
    }

    @Override
    public void accept(IRVisitor visitor) {
        visitor.visit(this);
    }

    @Override
    public String toString() {
        StringBuilder ret = new StringBuilder("%s = getelementptr %s, %s".formatted(reg, pointToType, pointer.toTypedFormat()));
        for (var index : indices)
            ret.append(", ").append(index.toTypedFormat());
        return ret.toString();
    }

    @Override
    public HashSet<IREntity> getUse() {
        HashSet<IREntity> ret = new HashSet<>(indices);
        ret.add(pointer);
        return ret;
    }

    @Override
    public void replaceUse(IREntity val0, IREntity val1) {
        if (pointer == val0) pointer = val1;
        for (int i = 0; i < indices.size(); i++) if (indices.get(i) == val0) indices.set(i, val1);
    }
}
