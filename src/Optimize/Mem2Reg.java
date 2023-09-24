package Optimize;

import IR.*;
import IR.Inst.*;
import IR.Entity.*;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;

public class Mem2Reg {
    private final IRProgram program;
    private IRFunction currentFunc;
    private HashSet<IRRegister> promotable = new HashSet<>();

    private HashMap<IRRegister, IREntity> replacement = new HashMap<>();

    public Mem2Reg(IRProgram program) {
        this.program = program;
    }

    public void work() {
        program.funcs.forEach(this::visit);
    }

    private void visit(IRFunction it) {
        currentFunc = it;
        promotable.clear();
        replacement.clear();
        it.allocaInsts.forEach(this::promote);
        renameVars(it.entryBlock());
    }

    private void promote(IRAllocaInst it) {
        if (!isPromotable(it)) return; // TODO
        LinkedList<IRBasicBlock> queue = new LinkedList<>();
        for (var block : currentFunc.blocks) {
            for (var inst : block.insts) {
                if (inst instanceof IRStoreInst storeInst && storeInst.val == it.reg) {
                    queue.add(block);
                }
            }
        }

        HashSet<IRBasicBlock> hasInsertedPhi = new HashSet<>();
        HashSet<IRBasicBlock> visited = new HashSet<>();
        while (!queue.isEmpty()) {
            for (var block : queue.removeFirst().domFrontier) {
                if (hasInsertedPhi.contains(block)) continue;
                block.addInst(new IRPhiInst(new IRRegister(it.reg.name, it.type), it.reg)); // TODO (name not needed)
                hasInsertedPhi.add(block);
                if (!visited.contains(block)) {
                    visited.add(block);
                    queue.add(block);
                }
            }
        }
    }

    private boolean isPromotable(IRAllocaInst it) {
        if (it.paramIndex >= 8) return false;
        for (var block : currentFunc.blocks) {
            for (var inst : block.insts) {
                if (!(inst instanceof IRLoadInst) && !(inst instanceof IRStoreInst) && inst.getUse().contains(it.reg)) {
                    return false;
                }
            }
        }
        promotable.add(it.reg);
        return true;
    }

    private void renameVars(IRBasicBlock it) {
        var temp = new HashMap<>(replacement);


        replacement = temp;
    }
}
