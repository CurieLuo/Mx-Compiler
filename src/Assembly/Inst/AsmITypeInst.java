package Assembly.Inst;

import Assembly.Operand.Imm;
import Assembly.Operand.Reg;

public class AsmITypeInst extends AsmInst {
    public String op;

    public AsmITypeInst(String op, Reg rd, Reg rs1, Imm imm) {
        this.op = switch (op) {
            case "sdivi", "sremi" -> op.substring(1);
            case "shli" -> "slli";
            case "ashri" -> "srai";
            default -> op;
        };
        this.rd = rd;
        this.rs1 = rs1;
        this.imm = imm;
    }

    @Override
    public String toString() {
        return "%s %s, %s, %s".formatted(op, rd, rs1, imm);
    }
}
