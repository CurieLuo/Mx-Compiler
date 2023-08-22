package Assembly.Inst;

import Assembly.Operand.Imm;
import Assembly.Operand.Reg;

public class AsmLaInst extends AsmInst {
    public String symbol;

    public AsmLaInst(Reg rd, String symbol) {
        this.rd = rd;
        this.symbol = symbol;
    }

    @Override
    public String toString() {
        return "la %s, %s".formatted(rd, symbol);
    }
}
