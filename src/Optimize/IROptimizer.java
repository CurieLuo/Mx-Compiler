package Optimize;

import IR.IRProgram;

public class IROptimizer {
    private final IRProgram program;

    public IROptimizer(IRProgram program) {
        this.program = program;
    }

    public void work() {
        new CFGBuilder(program).work();
        new DomTreeBuilder(program).work();
        new Mem2Reg(program).work();
    }
}
