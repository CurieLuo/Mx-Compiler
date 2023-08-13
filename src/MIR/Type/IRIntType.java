package MIR.Type;

import MIR.Entity.Entity;
import Util.Builtins;

public class IRIntType extends IRType {
    public int bits;

    public IRIntType(int bits) {
        super("i" + bits, bits / 8);
        this.bits = bits;
    }

    @Override
    public Entity defaultValue() {
        return Builtins.irInt0;
    }
}
