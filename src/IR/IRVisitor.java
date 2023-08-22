package IR;

import IR.Inst.*;

public interface IRVisitor {
    public void visit(IRProgram it);

    public void visit(IRFunction it);

    public void visit(IRBasicBlock it);

    public void visit(IRAllocaInst it);

    public void visit(IRBinaryInst it);

    public void visit(IRBranchInst it);

    public void visit(IRCallInst it);

    public void visit(IRGetElementPtrInst it);

    public void visit(IRIcmpInst it);

    public void visit(IRJumpInst it);

    public void visit(IRLoadInst it);

    public void visit(IRPhiInst it);

    public void visit(IRRetInst it);

    public void visit(IRStoreInst it);
}
