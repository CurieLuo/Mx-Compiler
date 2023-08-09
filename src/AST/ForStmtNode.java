package AST;

import Util.position;

public class ForStmtNode extends LoopStmtNode {
    public StmtNode init = null;
    public ExprNode step = null;

    public ForStmtNode(position pos) {
        super(pos);
    }

    @Override
    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }
}
