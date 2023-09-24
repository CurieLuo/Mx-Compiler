package IR.Inst;

import IR.Entity.IREntity;
import IR.Entity.IRRegister;
import IR.IRVisitor;

import java.util.ArrayList;
import java.util.HashSet;

public class IRCallInst extends IRInst {
    public IRRegister reg;
    public String name;
    public ArrayList<IREntity> args = new ArrayList<>();

    public IRCallInst(IRRegister reg, String name, IREntity... args) {
        this.reg = reg;
        this.name = name;
        for (var arg : args) addArg(arg);
    }

    public void addArg(IREntity arg) {
        args.add(arg);
    }

    @Override
    public void accept(IRVisitor visitor) {
        visitor.visit(this);
    }

    @Override
    public String toString() {
        StringBuilder ret = new StringBuilder(reg == null ? "call void " : "%s = call %s ".formatted(reg, reg.type));
        ret.append("@%s(".formatted(name));
        for (int i = 0; i < args.size(); i++) {
            if (i != 0) ret.append(", ");
            ret.append(args.get(i).toTypedFormat());
        }
        ret.append(")");
        return ret.toString();
    }

    @Override
    public HashSet<IREntity> getUse() {
        HashSet<IREntity> ret = new HashSet<>(args);
        return ret;
    }
}
