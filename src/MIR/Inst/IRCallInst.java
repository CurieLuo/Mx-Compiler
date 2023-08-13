package MIR.Inst;

import MIR.BasicBlock;
import MIR.Entity.Entity;
import MIR.Entity.IRRegister;
import MIR.Type.IRPtrType;

import java.util.ArrayList;

public class IRCallInst extends IRInst {
    public IRRegister reg;
    public String name;
    public ArrayList<Entity> args = new ArrayList<>();

    public IRCallInst(BasicBlock parentBlock, IRRegister reg, IRPtrType pointer) {
        super(parentBlock);
        this.reg = reg;
    }

    public void addArg(Entity arg) {
        args.add(arg);
    }

    @Override
    public String toString() {
        String ret = reg == null ? "call void" : "%s = call %s".formatted(reg, reg.type);
        ret += "@%s(".formatted(name);
        for (int i = 0, n = args.size(); i < n; i++) {
            if (i != 0) ret += ", ";
            ret += args.get(i).toTypedFormat();
        }
        ret += ")";
        return ret;
    }
}
