package AST;

import Util.position;

public class BinaryExprNode extends ExprNode {
    public ExprNode left, right;

    public String op;

    public BinaryExprNode(position pos) {
        super(pos);
    }

    @Override
    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }
}
