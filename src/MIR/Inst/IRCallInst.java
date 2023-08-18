package MIR.Inst;

import MIR.Entity.Entity;
import MIR.Entity.IRRegister;

import java.util.ArrayList;

public class IRCallInst extends IRInst {
    public IRRegister reg;
    public String name;
    public ArrayList<Entity> args = new ArrayList<>();

    public IRCallInst(IRRegister reg, String name) {
        super();
        this.reg = reg;
        this.name = name;
    }

    public IRCallInst(IRRegister reg, String name, Entity... args) {
        super();
        this.reg = reg;
        this.name = name;
        for (var arg : args) addArg(arg);
    }

    public void addArg(Entity arg) {
        args.add(arg);
    }

    @Override
    public String toString() {
        String ret = reg == null ? "call void " : "%s = call %s ".formatted(reg, reg.type);
        ret += "@%s(".formatted(name);
        for (int i = 0, n = args.size(); i < n; i++) {
            if (i != 0) ret += ", ";
            ret += args.get(i).toTypedFormat();
        }
        ret += ")";
        return ret;
    }
}
