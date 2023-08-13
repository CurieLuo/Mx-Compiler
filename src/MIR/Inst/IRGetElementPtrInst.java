package MIR.Inst;

import MIR.BasicBlock;
import MIR.Entity.Entity;
import MIR.Entity.IRRegister;
import MIR.Type.IRPtrType;
import MIR.Type.IRType;

import java.util.ArrayList;

public class IRGetElementPtrInst extends IRInst {
    public IRRegister reg;
    public IRType pointToType;
    public Entity pointer;

    public ArrayList<Entity> indices = new ArrayList<>();

    public IRGetElementPtrInst(BasicBlock parentBlock, IRRegister reg, Entity pointer) {
        super(parentBlock);
        this.reg = reg;
        this.pointer = pointer;
        this.pointToType = ((IRPtrType) pointer.type).pointToType();
    }

    public void addIndex(Entity index) {
        indices.add(index);
    } //TODO int type only

    @Override
    public String toString() {
        String ret = "%s = getelementptr %s, %s".formatted(reg, pointToType, pointer.toTypedFormat());
        for (var index : indices) ret += ", " + index.toTypedFormat();
        return ret;
    }
}
