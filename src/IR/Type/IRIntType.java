package IR.Type;

import IR.Entity.IRConst;
import Util.Builtins;

public class IRIntType extends IRType {
    public int bits;

    public IRIntType(int bits) {
        super("i" + bits, (bits + 7) / 8);
        this.bits = bits;
    }

    @Override
    public IRConst defaultValue() {
        return size == 4 ? Builtins.irInt0 : Builtins.irBoolFalse;
    }
}
