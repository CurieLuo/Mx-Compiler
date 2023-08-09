package AST;

import Util.position;

public class TernaryExprNode extends ExprNode {
    public ExprNode condition, left, right;

    public TernaryExprNode(position pos) {
        super(pos);
    }

    @Override
    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }
}
