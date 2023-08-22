package IR.Inst;

import IR.IRBasicBlock;
import IR.Entity.Entity;
import IR.Entity.IRRegister;
import IR.IRVisitor;

import java.util.ArrayList;

public class IRPhiInst extends IRInst {
    public IRRegister reg;
    public ArrayList<Entity> vals = new ArrayList<>();
    public ArrayList<IRBasicBlock> sourceBlocks = new ArrayList<>();

    public IRPhiInst(IRRegister reg) {
        this.reg = reg;
    }

    public void addSource(IRBasicBlock src, Entity val) {
        sourceBlocks.add(src);
        vals.add(val);
    }

    @Override
    public void accept(IRVisitor visitor) {
        visitor.visit(this);
    }

    @Override
    public String toString() {
        String ret = "%s = phi %s ".formatted(reg, reg.type);
        for (int i = 0; i < vals.size(); i++) {
            if (i != 0) ret += ", ";
            ret += "[ %s, %%%s ]".formatted(vals.get(i), sourceBlocks.get(i).label);
        }
        return ret;
    }
}
