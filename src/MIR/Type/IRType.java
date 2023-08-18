package MIR.Type;

import MIR.Entity.Entity;
import MIR.Entity.IREmptyConst;
//import MIR.Entity.IRNullConst;

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
    } //debug

    public Entity defaultValue() {
        return new IREmptyConst(this);
    }
}
