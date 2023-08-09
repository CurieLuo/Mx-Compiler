package AST;

import Util.position;

public class IfStmtNode extends StmtNode {

    public ExprNode condition;

    public StmtNode trueStmt, falseStmt = null;

    public IfStmtNode(position pos) {
        super(pos);
    }

    @Override
    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }
}
