package IR.Type;

import IR.Entity.IRConst;
import IR.Entity.IREmptyConst;

public abstract class IRType {
    public String name;
    public int size; // number of bytes

    public IRType(String name, int size) {
        this.name = name;
        this.size = size;
    }

    @Override
    public String toString() {
        return this.name;
    }

    public IRConst defaultValue() {
        return new IREmptyConst(this);
    }
}
