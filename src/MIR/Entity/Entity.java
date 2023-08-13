package MIR.Entity;

import MIR.Type.IRType;

public abstract class Entity {
    public IRType type;

    public Entity(IRType type) {
        this.type = type;
    }

    @Override
    public abstract String toString(); //debug

    public String toTypedFormat() {
        return type + " " + toString();
    }
}
