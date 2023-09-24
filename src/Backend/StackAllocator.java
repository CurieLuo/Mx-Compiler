package Backend;

import Assembly.*;
import Assembly.Inst.*;
import Assembly.Operand.*;

import static Assembly.Operand.PhyReg.*;

public class StackAllocator {
    private final AsmModule module;

    public StackAllocator(AsmModule module) {
        this.module = module;
    }

    public void work() {
        for (var func : module.funcs) {
            int stackSize = func.stackSize();
            if (stackSize < 1 << 11) {
                func.addFirstInst(new AsmITypeInst("addi", sp, sp, new Imm(-stackSize)));
                func.addLastInst(new AsmITypeInst("addi", sp, sp, new Imm(stackSize)));
            } else {
                func.addFirstInst(new AsmRTypeInst("add", sp, sp, t0));
                func.addFirstInst(new AsmLiInst(t0, new Imm(-stackSize)));

                func.addLastInst(new AsmLiInst(t0, new Imm(stackSize)));
                func.addLastInst(new AsmRTypeInst("add", sp, sp, t0));
            }
            func.addLastInst(new AsmRetInst());
        }
    }
}
