package AST;

import IR.IRBasicBlock;
import Util.position;

public abstract class LoopStmtNode extends StmtNode {
    public StmtNode body = null;
    public ExprNode condition = null;
    public IRBasicBlock condBlock = null, bodyBlock, stepBlock = null, endBlock;

    public LoopStmtNode(position pos) {
        super(pos);
    }
}
