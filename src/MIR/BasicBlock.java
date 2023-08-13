package MIR;

import MIR.Inst.IRAllocaInst;
import MIR.Inst.IRInst;
import MIR.Inst.IRTerminatorInst;

import java.util.LinkedList;

public class BasicBlock {
    public String label;
    public LinkedList<IRInst> insts = new LinkedList<>();
    public IRTerminatorInst terminatorInst = null;
    public Function parentFunc;

    private static int cnt = 0;

    public BasicBlock(Function parentFunc, String label) {
        this.parentFunc = parentFunc;
        if (!(label.equals("entry") || label.equals("return"))) label += cnt++;
        this.label = label;
    }

    public void addInst(IRInst inst) {
        if (inst instanceof IRTerminatorInst) {
            if (terminatorInst != null) terminatorInst = (IRTerminatorInst) inst;
        } else if (inst instanceof IRAllocaInst) {
            parentFunc.allocas.add((IRAllocaInst) inst);
        } else {
            insts.add(inst);
        }
    }

    @Override
    public String toString() {
        String ret = label + ":\n";
        for (var inst : insts) ret += "  " + inst + "\n";
        if (terminatorInst != null) ret += terminatorInst + "\n";
        return ret;
    } //debug
}
