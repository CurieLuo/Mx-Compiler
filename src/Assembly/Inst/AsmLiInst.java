package Assembly.Inst;

import Assembly.Operand.Imm;
import Assembly.Operand.Reg;

public class AsmLiInst extends AsmInst {

    public AsmLiInst(Reg rd, Imm imm) {
        this.rd = rd;
        this.imm = imm;
    }

    @Override
    public String toString() {
        return "li %s, %s".formatted(rd, imm);
    }
}
