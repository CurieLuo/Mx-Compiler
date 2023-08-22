package Assembly.Inst;

import Assembly.Operand.Reg;

public class AsmSetzInst extends AsmRTypeInst {

    public AsmSetzInst(String op, Reg rd, Reg rs1) {
        super("s" + op, rd, rs1, null);
    }

    @Override
    public String toString() {
        return "%sz %s, %s".formatted(op, rd, rs1);
    }
}
