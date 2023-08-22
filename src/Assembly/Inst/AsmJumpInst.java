package Assembly.Inst;

import Assembly.AsmBlock;
import Assembly.Operand.Imm;

public class AsmJumpInst extends AsmJTypeInst {
    public AsmBlock destBlock;

    public AsmJumpInst(AsmBlock destBlock) {
        this.destBlock = destBlock;
    }

    @Override
    public String toString() {
        return "j %s".formatted(destBlock.label);
    }
}
