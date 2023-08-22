package Assembly.Inst;

import Assembly.Operand.Imm;
import Assembly.Operand.Reg;

public class AsmStoreInst extends AsmInst {
    public String op;
    public String symbol = null;
    private static Imm imm0 = new Imm(0);

    public AsmStoreInst(int size, Reg rs2, Reg rs1, Imm imm) {
        this.op = "s" + (size == 1 ? "b" : "w");
        this.rs2 = rs2;
        this.rs1 = rs1;
        this.imm = imm;
    }

    public AsmStoreInst(int size, Reg rs2, Reg rs1) {
        this(size, rs2, rs1, imm0);
    }

    public AsmStoreInst(int size, Reg rs2, String symbol) {
        this(size, rs2, null, null);
        this.symbol = symbol;
    }

    @Override
    public String toString() {
        return symbol == null ? "%s %s, %s(%s)".formatted(op, rs2, imm, rs1) :
                "%s %s, %s".formatted(op, rs2, symbol);
    }
}
