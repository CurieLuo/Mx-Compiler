package IR.Entity;

import IR.Type.IRPtrType;
import IR.Type.IRType;

public class IRGlobalVar extends IRRegister {
    public IRConst initVal;

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
