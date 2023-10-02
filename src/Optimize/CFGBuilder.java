package Optimize;

import IR.*;
import IR.Inst.*;

import java.util.HashSet;
import java.util.LinkedList;

public class CFGBuilder {
    private final IRProgram program;

    public CFGBuilder(IRProgram program) {
        this.program = program;
    }

    public void work() {
        program.funcs.forEach(this::visit);
    }

    private void visit(IRFunction it) {
        if (it.isBuiltin) return;
        for (var block : it.blocks) {
            if (block.terminatorInst instanceof IRBranchInst branchInst) {
                block.addEdgeTo(branchInst.trueBlock);
                block.addEdgeTo(branchInst.falseBlock);
            } else if (block.terminatorInst instanceof IRJumpInst jumpInst) {
                block.addEdgeTo(jumpInst.destBlock);
            }
        }

        LinkedList<IRBasicBlock> blocks = new LinkedList<>();
        boolean changed = true;
        HashSet<IRBasicBlock> visited = new HashSet<>();
        while (changed) {
            changed = false;
            for (var block : it.blocks) {
                if (block != it.entryBlock() && block.pred.isEmpty() && !visited.contains(block)) {
                    visited.add(block);
                    changed = true;
                    for (var succ : block.succ) {
                        succ.pred.remove(block);
                    }
                }
            }
        }
        for (var block : it.blocks) {
            if (block == it.entryBlock() || !block.pred.isEmpty()) {
                blocks.add(block);
            }
        }
        it.blocks = blocks;
    }
}
