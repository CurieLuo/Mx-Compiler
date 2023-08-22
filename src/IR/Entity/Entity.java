package IR.Entity;

import Assembly.Operand.Reg;
import IR.Type.IRType;

public abstract class Entity {
    public IRType type;
    public Reg reg;

    public Entity(IRType type) {
        this.type = type;
    }

    @Override
    public abstract String toString(); //debug

    public String toTypedFormat() {
        return type + " " + toString();
    }
}
