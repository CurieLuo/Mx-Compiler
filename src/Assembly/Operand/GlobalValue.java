package Assembly.Operand;

import IR.Entity.IRGlobalVar;
import IR.Entity.IRStringConst;

public class GlobalValue extends GlobalSymbol {
    int val, size;

    public GlobalValue(IRGlobalVar var) {
        super(var.name);
        size = var.type.size;
        val = var.initVal.toInt();
    }

    @Override
    public String toString() {
        return "%s:\n  .%s %s\n".formatted(name, size == 1 ? "byte" : "word", val);
    }
}
