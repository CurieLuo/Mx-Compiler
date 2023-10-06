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
    private final HashSet<IRRegister> promotable = new HashSet<>();

    private HashMap<IRRegister, IREntity> replacement = new HashMap<>();

    public Mem2Reg(IRProgram program) {
        this.program = program;
    }

    public void work() {
        program.funcs.forEach(this::visit);
    }

    private void visit(IRFunction it) {
        if (it.isBuiltin) return;
        currentFunc = it;
        promotable.clear();
        replacement.clear();
        it.allocaInsts.forEach(this::promote);
        renameVars(it.entryBlock());
        finish(it.entryBlock());
    }

    private void promote(IRAllocaInst it) {
        if (!isPromotable(it)) return; // TODO
        LinkedList<IRBasicBlock> queue = new LinkedList<>();
        for (var block : currentFunc.blocks) {
            for (var inst : block.insts) {
                if (inst instanceof IRStoreInst storeInst && storeInst.pointer == it.reg) {
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
        LinkedList<IRInst> newInsts = new LinkedList<>();
        for (var inst : it.phiInsts)
            replacement.put(inst.allocaPointer, inst.reg);
        for (int i = 0; i < it.insts.size(); i++) {
            IRInst inst = it.insts.get(i);
            if (inst instanceof IRAllocaInst allocaInst && promotable.contains(allocaInst.reg)) {
                continue; // remove promoted alloca instructions
            } else if (inst instanceof IRLoadInst loadInst && promotable.contains(loadInst.pointer)) {
                var newReg = replacement.get(loadInst.pointer);
                for (int j = i + 1; j < it.insts.size(); j++) {
                    it.insts.get(j).replaceUse(loadInst.reg, newReg);
                }
                it.terminatorInst.replaceUse(loadInst.reg, newReg);
                for (var succ : it.succ) {
                    for (var inst_ : succ.insts) {
                        if (inst_ instanceof IRPhiInst phiInst) {
                            phiInst.replaceUse(loadInst.reg, newReg);
                        } else break;
                    }
                }
            } else if (inst instanceof IRStoreInst storeInst && promotable.contains(storeInst.pointer)) {
                replacement.put(storeInst.pointer, storeInst.val);
            } else {
                newInsts.add(inst);
            }
        }
        it.insts = newInsts;
        for (var succ : it.succ) {
            for (var phiInst : succ.phiInsts) {
                if (replacement.containsKey(phiInst.allocaPointer)) {
                    phiInst.addSource(it, replacement.get(phiInst.allocaPointer));
                }
            }
        }
        it.domChildren.forEach(this::renameVars);
        replacement = temp;
    }

    private void finish(IRBasicBlock it) {
        // TODO simplify
        for (int i = it.phiInsts.size() - 1; i >= 0; i--) {
            it.insts.addFirst(it.phiInsts.get(i));
        }
        it.domChildren.forEach(this::finish);
    }
}
