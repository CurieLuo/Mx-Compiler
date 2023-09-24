package IR.Inst;

import IR.Entity.Entity;
import IR.Entity.IRRegister;
import IR.IRVisitor;
import IR.Type.IRPtrType;
import IR.Type.IRType;

import java.util.LinkedList;

public class IRGetElementPtrInst extends IRInst {
    public IRRegister reg;
    public IRType pointToType;
    public Entity pointer;

    public LinkedList<Entity> indices = new LinkedList<>();

    public IRGetElementPtrInst(IRRegister reg, Entity pointer, Entity... indices) {
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
}
