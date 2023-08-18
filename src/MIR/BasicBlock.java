package MIR;

import MIR.Inst.IRAllocaInst;
import MIR.Inst.IRInst;
import MIR.Inst.IRJumpInst;
import MIR.Inst.IRTerminatorInst;
import Util.error.internalError;

import java.util.LinkedList;

public class BasicBlock {
    public String label;
    public LinkedList<IRInst> insts = new LinkedList<>();
    public IRTerminatorInst terminatorInst = null;
    public Function parentFunc;

    private static int cnt = 0;

    public BasicBlock(String label) {
        if (!(label.equals("entry") || label.equals("return"))) label += cnt++;
        this.label = label;
    }

    public void addInst(IRInst inst) {
        if (terminatorInst != null) return;
        inst.parentBlock = this;
        if (inst instanceof IRTerminatorInst) {
//            if (terminatorInst != null)
//                throw new internalError("more than one terminator instructions in block %" + label, null);
            terminatorInst = (IRTerminatorInst) inst;
        } else if (inst instanceof IRAllocaInst) {
            inst.parentBlock = parentFunc.entryBlock();
            parentFunc.allocas.add((IRAllocaInst) inst);
        } else {
            insts.add(inst);
        }
    }

    public void setJump(BasicBlock dest) {
        addInst(new IRJumpInst(dest));
    }

    public static void reset() {
        cnt = 0;
    }

    public boolean isEmpty() {
        return insts.isEmpty();
    }

    @Override
    public String toString() {
        String ret = label + ":\n";
        for (var inst : insts) ret += "  " + inst + "\n";
        if (terminatorInst != null) ret += "  " + terminatorInst + "\n";
        return ret;
    } //debug
}
