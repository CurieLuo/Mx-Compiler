package Assembly;

import Assembly.Inst.AsmBTypeInst;
import Assembly.Inst.AsmInst;
import Assembly.Inst.AsmJTypeInst;
import Assembly.Inst.AsmJumpInst;
import Assembly.Operand.GlobalSymbol;

import java.util.ArrayList;
import java.util.LinkedList;

public class AsmBlock {
    public String label;
    public LinkedList<AsmInst> insts = new LinkedList<>();
    private ArrayList<AsmInst> BnJInsts = new ArrayList<>(); // B-Type and J-Type
    public ArrayList<AsmInst> phiSrcInsts = new ArrayList<>();
    public LinkedList<AsmBlock> pred = new LinkedList<>(), succ = new LinkedList<>();
    private static int cnt = 0;

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
        String ret = label + ":\n";
        for (var inst : insts) ret += "  %s\n".formatted(inst);
        return ret;
    }
}
