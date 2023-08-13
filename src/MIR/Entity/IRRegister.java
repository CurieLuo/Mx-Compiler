package MIR.Entity;

import MIR.Type.IRType;

public class IRRegister extends Entity {
    public String name;
    public int id;
    protected static int cnt = 0;


    public IRRegister(String name, IRType type) {
        super(type);
        this.name = name;
        id = cnt++;
    }

    @Override
    public String toString() {
        return "%" + name + "." + id;
    }
}
