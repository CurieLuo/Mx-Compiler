package Assembly.Inst;

import Assembly.Operand.Imm;
import Assembly.Operand.Reg;

public class AsmLoadInst extends AsmITypeInst {
    public String symbol = null;
    private static Imm imm0 = new Imm(0);

    public AsmLoadInst(int size, Reg rd, Reg rs1, Imm imm) {
        super("l" + (size == 1 ? "b" : "w"), rd, rs1, imm);
    }

    public AsmLoadInst(int size, Reg rd, Reg rs1) {
        this(size, rd, rs1, imm0);
    }

    public AsmLoadInst(int size, Reg rd, String symbol) {
        this(size, rd, null, null);
        this.symbol = symbol;
    }

    @Override
    public String toString() {
        return symbol == null ? "%s %s, %s(%s)".formatted(op, rd, imm, rs1) :
                "%s %s, %s".formatted(op, rd, symbol);
    }
}
