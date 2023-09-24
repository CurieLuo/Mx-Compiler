package Optimize;

import IR.*;
import IR.Inst.*;

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
        for (var block : it.blocks) {
            if (block.terminatorInst instanceof IRBranchInst branchInst) {
                block.addEdgeTo(branchInst.trueBlock);
                block.addEdgeTo(branchInst.falseBlock);
            } else if (block.terminatorInst instanceof IRJumpInst jumpInst) {
                block.addEdgeTo(jumpInst.destBlock);
            }
        }

        LinkedList<IRBasicBlock> blocks = new LinkedList<>();
        for (var block : it.blocks) {
            if (block == it.entryBlock() || !block.pred.isEmpty()) {
                blocks.add(block);
            } else {
                for (var succ : block.succ) {
                    succ.pred.remove(block);
                }
            }
        }
        it.blocks = blocks;
    }
}
