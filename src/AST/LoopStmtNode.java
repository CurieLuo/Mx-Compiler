package AST;

import Util.position;

public abstract class LoopStmtNode extends StmtNode {
    public StmtNode body = null;
    public ExprNode condition = null;

    public LoopStmtNode(position pos) {
        super(pos);
    }
}
