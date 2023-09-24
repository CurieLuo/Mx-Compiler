package IR.Entity;

import IR.Type.IRType;

public class IRRegister extends IREntity {
    public String name;
    private int id = 0;
    protected static int tmpCnt = 0, cnt = 0;


    public IRRegister(IRType type) {
        super(type);
        this.name = "." + tmpCnt++;
    }

    public IRRegister(String name, IRType type) {
        super(type);
        this.name = name;
        if (!(name.equals("this") || name.contains("."))) id = cnt++;
    }

    @Override
    public String toString() {
        if (id == 0) return "%" + name;
        return "%" + name + "." + id;
    }

    public static void reset(int start) {
        tmpCnt = 0;
        cnt = start;
    }
}
