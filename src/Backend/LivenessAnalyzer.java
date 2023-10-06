package Backend;

import Assembly.Operand.Reg;
import IR.*;
import Assembly.*;

import java.util.HashSet;
import java.util.LinkedList;

public class LivenessAnalyzer {
    private final AsmFunction func;

    private final LinkedList<AsmBlock> queue = new LinkedList<>();
    private final HashSet<AsmBlock> inQueue = new HashSet<>();

    public LivenessAnalyzer(AsmFunction func) {
        this.func = func;
    }

    public void work() {
        for (var block : func.blocks) {
            block.liveIn.clear();
            block.liveOut.clear();
            block.def.clear();
            block.use.clear();
            for (var inst : block.insts) {
                for (var reg : inst.getUse()) {
                    if (!block.def.contains(reg)) {
                        block.use.add(reg);
                    }
                }
                block.def.addAll(inst.getDef());
            }
        }
        queue.clear();
        inQueue.clear();
        queue.add(func.returnBlock());
        inQueue.add(func.returnBlock());
        while (!queue.isEmpty()) {
            AsmBlock block = queue.removeFirst();
            inQueue.remove(block);
            HashSet<Reg> newLiveOut = new HashSet<>();
            for (var succ : block.succ) {
                newLiveOut.addAll(succ.liveIn);
            }
            HashSet<Reg> newLiveIn = new HashSet<>(newLiveOut);
            newLiveIn.removeAll(block.def);
            newLiveIn.addAll(block.use);
            if (newLiveIn != block.liveIn || newLiveOut != block.liveOut) {
                block.liveIn = newLiveIn;
                block.liveOut = newLiveOut;
                for (var pred : block.pred) {
                    if (!inQueue.contains(pred)) {
                        queue.add(pred);
                        inQueue.add(pred);
                    }
                }
            }
        }
    }
}
