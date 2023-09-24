package IR.Inst;

import IR.IRBasicBlock;
import IR.Entity.IREntity;
import IR.Entity.IRRegister;
import IR.IRVisitor;

import java.util.ArrayList;
import java.util.HashSet;

public class IRPhiInst extends IRInst {
    public IRRegister reg, allocaReg = null;
    public ArrayList<IREntity> vals = new ArrayList<>();
    public ArrayList<IRBasicBlock> sourceBlocks = new ArrayList<>();

    public IRPhiInst(IRRegister reg) {
        this.reg = reg;
    }

    public IRPhiInst(IRRegister reg, IRRegister allocaReg) {
        this(reg);
        this.allocaReg = allocaReg;
    }

    public void addSource(IRBasicBlock src, IREntity val) {
        sourceBlocks.add(src);
        vals.add(val);
    }

    @Override
    public void accept(IRVisitor visitor) {
        visitor.visit(this);
    }

    @Override
    public String toString() {
        StringBuilder ret = new StringBuilder("%s = phi %s ".formatted(reg, reg.type));
        for (int i = 0; i < vals.size(); i++) {
            if (i != 0) ret.append(", ");
            ret.append("[ %s, %%%s ]".formatted(vals.get(i), sourceBlocks.get(i).label));
        }
        return ret.toString();
    }

    @Override
    public HashSet<IREntity> getUse() {
        return new HashSet<>(vals);
    }
}
