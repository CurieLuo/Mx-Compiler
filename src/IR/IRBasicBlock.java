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

    // for optimization

    public LinkedList<IRBasicBlock> pred = new LinkedList<>(), succ = new LinkedList<>();

    public void addEdgeTo(IRBasicBlock dest) {
        // add Edge from this to dest in CFG
        succ.add(dest);
        dest.pred.add(this);
    }

    public IRBasicBlock idom = null; // immediate dominator
    public LinkedList<IRBasicBlock> domFrontier = new LinkedList<>(), domChildren = new LinkedList<>();

    public IRBasicBlock(String label) {
        if (!(label.equals("entry") || label.equals("return"))) label += cnt++;
        this.label = label;
    }

    public void addInst(IRInst inst) {
        inst.parentBlock = this;
        if (inst instanceof IRTerminatorInst) {
            terminatorInst = (IRTerminatorInst) inst;
        } else if (inst instanceof IRAllocaInst) {
            inst.parentBlock = parentFunc.entryBlock();
            parentFunc.allocaInsts.add((IRAllocaInst) inst);
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
        StringBuilder ret = new StringBuilder(label + ":\n");
        for (var inst : insts) ret.append("  ").append(inst).append("\n");
        if (terminatorInst != null) ret.append("  ").append(terminatorInst).append("\n");
        return ret.toString();
    }
}
