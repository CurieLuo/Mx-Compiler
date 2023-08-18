package MIR.Inst;

import MIR.Entity.Entity;
import MIR.Entity.IRRegister;
import MIR.Type.IRPtrType;
import MIR.Type.IRType;
import Util.Builtins;

import java.util.ArrayList;

public class IRGetElementPtrInst extends IRInst {
    public IRRegister reg;
    public IRType pointToType;
    public Entity pointer;

//    Entity index;

    public ArrayList<Entity> indices = new ArrayList<>();

    public IRGetElementPtrInst(IRRegister reg, Entity pointer, Entity... indices) {
        super();
        this.reg = reg;
        this.pointer = pointer;
        this.pointToType = ((IRPtrType) pointer.type).pointToType();
        for (var index : indices)
            this.indices.add(index);
        //TODO one or two indices
    }

//    public IRGetElementPtrInst(IRRegister reg, Entity pointer, IRType pointToType, Entity index) {
//        super();
//        this.reg = reg;
//        this.pointer = pointer;
//        this.pointToType = pointToType;
//        this.index = index;
//    }

//    public void addIndex(Entity index) {
//        indices.add(index);
//    }

    @Override
    public String toString() {
        String ret = "%s = getelementptr %s, %s".formatted(reg, pointToType, pointer.toTypedFormat());
        for (var index : indices)
            ret += ", " + index.toTypedFormat();
        return ret;
    }
}
