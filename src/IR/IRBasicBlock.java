package IR;

import IR.Inst.IRAllocaInst;
import IR.Inst.IRInst;
import IR.Inst.IRJumpInst;
import IR.Inst.IRTerminatorInst;

import java.util.LinkedList;

public class IRBasicBlock {
    public String label;
    public LinkedList<IRInst> insts = new LinkedList<>();
    public IRTerminatorInst terminatorInst = null;
    public IRFunction parentFunc;

    private static int cnt = 0;

    public IRBasicBlock(String label) {
        if (!(label.equals("entry") || label.equals("return"))) label += cnt++;
        this.label = label;
    }

    public void addInst(IRInst inst) {
        if (terminatorInst != null) return;
        inst.parentBlock = this;
        if (inst instanceof IRTerminatorInst) {
            terminatorInst = (IRTerminatorInst) inst;
        } else if (inst instanceof IRAllocaInst) {
            inst.parentBlock = parentFunc.entryBlock();
            parentFunc.allocas.add((IRAllocaInst) inst);
        } else {
            insts.add(inst);
        }
    }

    public void setJump(IRBasicBlock dest) {
        addInst(new IRJumpInst(dest));
    }

    public static void reset() {
        cnt = 0;
    }

    public boolean isEmpty() {
        return insts.isEmpty();
    }

    public void accept(IRVisitor visitor) {
        visitor.visit(this);
    }

    @Override
    public String toString() {
        String ret = label + ":\n";
        for (var inst : insts) ret += "  " + inst + "\n";
        if (terminatorInst != null) ret += "  " + terminatorInst + "\n";
        return ret;
    }
}
