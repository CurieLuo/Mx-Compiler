package Assembly.Inst;

import Assembly.Operand.Reg;

public class AsmMvInst extends AsmRTypeInst {

    public AsmMvInst(Reg rd, Reg rs1) {
        super("add", rd, rs1, null);
    }

    @Override
    public String toString() {
        return "mv %s, %s".formatted(rd, rs1);
    }
}
