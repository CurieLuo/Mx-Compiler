package Assembly.Inst;

import Assembly.Operand.Imm;
import Assembly.Operand.Reg;

import java.util.HashSet;

public abstract class AsmInst {
    public Reg rd, rs1, rs2;
    public Imm imm;

    @Override
    public abstract String toString();

    public HashSet<Reg> getDef() {
        HashSet<Reg> ret = new HashSet<>();
        if (rd != null) ret.add(rd);
        return ret;
    }

    public HashSet<Reg> getUse() {
        HashSet<Reg> ret = new HashSet<>();
        if (rs1 != null) ret.add(rs1);
        if (rs2 != null) ret.add(rs2);
        return ret;
    }
}
