// https://blog.csdn.net/dashuniuniu/article/details/52224882

package Optimize;

import IR.*;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;

public class DomTreeBuilder {
    private final IRProgram program;

    private HashSet<IRBasicBlock> visited = new HashSet<>();
    private HashMap<IRBasicBlock, Integer> order = new HashMap<>();
    private LinkedList<IRBasicBlock> blocks = new LinkedList<>();
    // converges faster if iterated in reversed post order

    public DomTreeBuilder(IRProgram program) {
        this.program = program;
    }

    public void work() {
        program.funcs.forEach(this::visit);
    }

    private void visit(IRFunction it) {
        visited.clear();
        order.clear();
        blocks.clear();
        getOrder(it.entryBlock());
        for (int i = 0; i < blocks.size(); i++) order.put(blocks.get(i), i);
        it.entryBlock().idom = it.entryBlock();
        blocks.removeFirst();
        boolean changed = true;
        while (changed) {
            changed = false;
            for (var block : blocks) {
                IRBasicBlock newIdom = null;
                for (var pred : block.pred) {
                    if (pred.idom != null) {
                        newIdom = pred;
                        break;
                    }
                } // initial value
                for (var pred : block.pred) {
                    if (pred.idom != null) {
                        newIdom = intersect(newIdom, pred);
                    }
                }
                if (newIdom != block.idom) {
                    block.idom = newIdom;
                    changed = true;
                }
            }
            for (var block : blocks) block.idom.domChildren.add(block);
            // calculate dominance frontier
            for (var block : blocks) {
                if (block.pred.size() < 2) continue;
                for (var pred : block.pred) {
                    var runner = pred;
                    while (runner != block.idom) {
                        runner.domFrontier.add(block);
                        runner = runner.idom;
                    }
                }
            }
        }
    }

    private void getOrder(IRBasicBlock it) {
        visited.add(it);
        for (var succ : it.succ) if (!visited.contains(succ)) getOrder(succ);
        blocks.addFirst(it);
    }

    private IRBasicBlock intersect(IRBasicBlock b1, IRBasicBlock b2) {
        // least common ancestor on dominator tree
        while (b1 != b2) {
            while (order.get(b1) < order.get(b2)) b1 = b1.idom;
            while (order.get(b2) < order.get(b1)) b2 = b2.idom;
        }
        return b1;
    }

}