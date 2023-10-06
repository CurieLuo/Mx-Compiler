package Optimize;

import IR.IRFunction;
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
//        program.funcs.forEach(IRFunction::finish); // not needed if mem2reg is switched on
    }
}
