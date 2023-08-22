package Assembly.Inst;

import Assembly.AsmBlock;
import Assembly.Operand.Reg;

public class AsmBeqzInst extends AsmBTypeInst {
    public AsmBeqzInst(Reg rs1, AsmBlock destBlock) {
        super("eq", rs1, null, destBlock);
    }

    @Override
    public String toString() {
        return "b%sz %s, %s".formatted(op, rs1, destBlock.label);
    }
}
