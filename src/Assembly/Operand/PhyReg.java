package Assembly.Operand;

import java.util.ArrayList;
import java.util.HashMap;

public class PhyReg extends Reg {
    public String name;
    int id;

    private static final ArrayList<PhyReg> regsList = new ArrayList<>();
    private static final HashMap<String, PhyReg> regsMap = new HashMap<>();

    public static ArrayList<PhyReg> calleeSaved = new ArrayList<>();

    public PhyReg(String name, int id) {
        this.name = name;
        this.id = id;
    }

    private static PhyReg add(String name) {
        PhyReg reg = new PhyReg(name, regsList.size());
        regsMap.put(name, reg);
        regsList.add(reg);
        return reg;
    }

    public static PhyReg zero, ra, sp, t0, t1, t2, a0;

    static {
        zero = add("zero");
        ra = add("ra");
        sp = add("sp");
        add("gp");
        add("tp");
        for (int i = 0; i < 3; i++) add("t" + i);
        for (int i = 0; i < 2; i++) calleeSaved.add(add("s" + i));
        for (int i = 0; i < 8; i++) add("a" + i);
        for (int i = 2; i < 12; i++) calleeSaved.add(add("s" + i));
        for (int i = 3; i < 7; i++) add("t" + i);

        t0 = get("t0");
        t1 = get("t1");
        t2 = get("t2");
        a0 = get("a0");
    }

    public static PhyReg get(String name) {
        return regsMap.get(name);
    }

    public static PhyReg get(int id) {
        return regsList.get(id);
    }

    @Override
    public String toString() {
        return name;
    }
}
