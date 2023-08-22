package Assembly.Inst;

import Assembly.AsmBlock;
import Assembly.Operand.Reg;

public class AsmCallInst extends AsmInst {
    // use ra
    String funcName;

    public AsmCallInst(String funcName) {
        this.funcName = funcName;
    }

    @Override
    public String toString() {
        return "call %s".formatted(funcName);
    }
}
