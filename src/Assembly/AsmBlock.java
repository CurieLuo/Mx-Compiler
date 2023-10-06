package Assembly;

import Assembly.Inst.AsmBTypeInst;
import Assembly.Inst.AsmInst;
import Assembly.Inst.AsmJTypeInst;
import Assembly.Operand.Reg;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;

public class AsmBlock {
    public String label;
    public LinkedList<AsmInst> insts = new LinkedList<>();
    private ArrayList<AsmInst> BnJInsts = new ArrayList<>(); // B-Type and J-Type
    public ArrayList<AsmInst> phiSrcInsts = new ArrayList<>();
    private static int cnt = 0;

    // for liveness analysis & register allocation
    public LinkedList<AsmBlock> pred = new LinkedList<>(), succ = new LinkedList<>();
    public HashSet<Reg> def = new HashSet<>(), use = new HashSet<>(),
            liveIn = new HashSet<>(), liveOut = new HashSet<>();

    public AsmBlock() {
        label = ".LBB_" + cnt++;
    }

    public AsmBlock(String name) {
        label = name;
    }

    public void addInst(AsmInst inst) {
        if (inst instanceof AsmBTypeInst || inst instanceof AsmJTypeInst) BnJInsts.add(inst);
        else insts.add(inst);
    }

    public void finish() {
        insts.addAll(phiSrcInsts);
        insts.addAll(BnJInsts);
    }

    @Override
    public String toString() {
        StringBuilder ret = new StringBuilder(label + ":\n");
        for (var inst : insts) ret.append("  %s\n".formatted(inst));
        return ret.toString();
    }
}
