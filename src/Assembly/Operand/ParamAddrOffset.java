package Assembly.Operand;

import Assembly.AsmFunction;

public class ParamAddrOffset extends Imm {

    private AsmFunction func; // callee function

    public ParamAddrOffset(AsmFunction func, int offset) {
        super(offset);
        this.func = func;
    }

    public int getValue() {
        if (func != null) {
            value += func.stackSize();
            // calculates the offset for the sp of the caller function
            func = null;
        }
        return value;
    }

    public String toString() {
        return Integer.toString(getValue());
    }
}
