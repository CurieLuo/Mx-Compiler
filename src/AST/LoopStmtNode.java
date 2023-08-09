package AST;

import Util.position;

abstract public class LoopStmtNode extends StmtNode {
    public StmtNode body = null;
    public ExprNode condition = null;

    public LoopStmtNode(position pos) {
        super(pos);
    }
}
