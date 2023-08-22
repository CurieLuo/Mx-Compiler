package Assembly.Inst;

import Assembly.Operand.Imm;
import Assembly.Operand.Reg;

public abstract class AsmInst {
    public Reg rd, rs1, rs2;
    public Imm imm;

    @Override
    public abstract String toString();
}
