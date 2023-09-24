package Optimize;

import IR.*;
import IR.Inst.*;
import IR.Entity.*;

public class Mem2Reg {
    private final IRProgram program;
    private IRFunction currentFunc;

    public Mem2Reg(IRProgram program) {
        this.program = program;
    }

    public void work() {
        program.funcs.forEach(this::visit);
    }

    private void visit(IRFunction it) {
        currentFunc = it;
    }
}
