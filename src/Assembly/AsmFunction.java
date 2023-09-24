package Assembly;

import Assembly.Inst.AsmInst;

import java.util.ArrayList;

public class AsmFunction {
    public String name;
    public ArrayList<AsmBlock> blocks = new ArrayList<>();

    public int virtualRegCount = 0;
    public int paramSpaceSize = 0, allocSize = 4, spillingSize = 0;

    // stack storage from bottom to top: params, alloc (starting with ra)
    public int stackSize() {
        return paramSpaceSize + allocSize + spillingSize;
    }

    public AsmFunction(String name) {
        this.name = name;
    }

    public AsmBlock entryBlock() {
        return blocks.get(0);
    }

    public AsmBlock returnBlock() {
        return blocks.get(1);
    }

    public void addFirstInst(AsmInst inst) {
        entryBlock().insts.addFirst(inst);
    }

    public void addLastInst(AsmInst inst) {
        returnBlock().insts.addLast(inst);
    }

    @Override
    public String toString() {
        StringBuilder ret = new StringBuilder("  .globl %s \n".formatted(name));
        for (var block : blocks) ret.append(block);
        return ret.toString();
    }
}
