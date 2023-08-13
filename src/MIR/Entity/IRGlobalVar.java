package MIR.Entity;

import MIR.Type.IRPtrType;
import MIR.Type.IRType;

public class IRGlobalVar extends IRRegister {
    public Entity initVal;

    public IRGlobalVar(String name, IRType type) {
        super(name, new IRPtrType(type));
        initVal = type.defaultValue();
        cnt--;
    }

    @Override
    public String toString() {
        return "@" + name;
    }
}
