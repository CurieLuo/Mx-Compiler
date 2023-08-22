package Assembly.Inst;

import Assembly.AsmBlock;
import Assembly.Operand.Reg;
import IR.Inst.IRIcmpInst;

public class AsmBTypeInst extends AsmInst {
    public String op;
    public AsmBlock destBlock;

    public AsmBTypeInst(String op, Reg rs1, Reg rs2, AsmBlock destBlock) {
        this.op = IRIcmpInst.toShortForm(op);
        this.rs1 = rs1;
        this.rs2 = rs2;
        this.destBlock = destBlock;
    }

    @Override
    public String toString() {
        return "b%s %s, %s".formatted(op, rs1, destBlock.label);
    }
}
