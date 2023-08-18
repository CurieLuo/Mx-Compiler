package MIR.Inst;

import MIR.BasicBlock;
import MIR.Entity.Entity;
import MIR.Entity.IRRegister;

import java.util.ArrayList;

public class IRPhiInst extends IRInst {
    public IRRegister reg;
    public ArrayList<Entity> vals = new ArrayList<>();
    public ArrayList<BasicBlock> sourceBlocks = new ArrayList<>();

    public IRPhiInst(IRRegister reg) {
        super();
        this.reg = reg;
    }

    public void addSource(BasicBlock src, Entity val) {
        sourceBlocks.add(src);
        vals.add(val);
    }

    @Override
    public String toString() {
        String ret = "%s = phi %s ".formatted(reg, reg.type);
        for (int i = 0, n = vals.size(); i < n; i++) {
            if (i != 0) ret += ", ";
            ret += "[ %s, %%%s ]".formatted(vals.get(i), sourceBlocks.get(i).label);
        }
        return ret;
    }
}
