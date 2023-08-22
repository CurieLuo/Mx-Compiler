package Assembly.Inst;

import Assembly.Operand.Reg;

public class AsmRTypeInst extends AsmInst {
    public String op;

    public AsmRTypeInst(String op, Reg rd, Reg rs1, Reg rs2) {
        this.op = switch (op) {
            case "sdiv", "srem" -> op.substring(1);
            case "shl" -> "sll";
            case "ashr" -> "sra";
            default -> op;
        };
        this.rd = rd;
        this.rs1 = rs1;
        this.rs2 = rs2;
    }

    @Override
    public String toString() {
        return "%s %s, %s, %s".formatted(op, rd, rs1, rs2);
    }
}
