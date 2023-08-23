package Backend;

import Assembly.*;
import Assembly.Inst.*;
import Assembly.Operand.*;

import java.util.LinkedList;

import static Assembly.Operand.PhyReg.*;

public class NaiveRegAllocator {
    private AsmModule module;
    private int initialAddrOffset;
    private LinkedList<AsmInst> newInsts;

    public NaiveRegAllocator(AsmModule module) {
        this.module = module;
    }

    public void work() {
        for (var func : module.funcs) {
            initialAddrOffset = func.stackSize();
            for (var block : func.blocks) {
                visit(block);
            }
            func.spillingSize = func.virtualRegCount * 4;
        }
    }

    private void visit(AsmBlock it) {
        newInsts = new LinkedList<>();
        for (var inst : it.insts) {
            inst.rs1 = allocSrc(inst.rs1, t1);
            inst.rs2 = allocSrc(inst.rs2, t2);
            newInsts.add(inst);
            inst.rd = allocDest(inst.rd, t1);
        }
        it.insts = newInsts;
    }

    private Reg allocSrc(Reg regToAllocate, PhyReg ret) {
        if (regToAllocate instanceof VirtualReg virtualReg) {
            int offset = initialAddrOffset + virtualReg.id * 4;
            if (offset < 1 << 11) {
                newInsts.add(new AsmLoadInst(4, ret, sp, new Imm(offset)));
            } else {
                newInsts.add(new AsmLiInst(t0, new Imm(offset)));
                newInsts.add(new AsmRTypeInst("add", t0, t0, sp));
                newInsts.add(new AsmLoadInst(4, ret, t0));
            }
            return ret;
        } else return regToAllocate;
    }

    private Reg allocDest(Reg regToAllocate, PhyReg ret) {
        if (regToAllocate instanceof VirtualReg virtualReg) {
            int offset = initialAddrOffset + virtualReg.id * 4;
            if (offset < 1 << 11) {
                newInsts.add(new AsmStoreInst(4, ret, sp, new Imm(offset)));
            } else {
                newInsts.add(new AsmLiInst(t0, new Imm(offset)));
                newInsts.add(new AsmRTypeInst("add", t0, t0, sp));
                newInsts.add(new AsmStoreInst(4, ret, t0));
            }
            return ret;
        } else return regToAllocate;
    }
}
