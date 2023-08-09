package AST;

import Util.position;

public class UnaryExprNode extends ExprNode {
    public ExprNode obj;

    public String op;

    public UnaryExprNode(position pos) {
        super(pos);
    }

    @Override
    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }
}
