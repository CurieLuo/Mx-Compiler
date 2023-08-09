package AST;

import Util.position;

public class ArrayExprNode extends ExprNode {
    public ExprNode obj, index;

    public ArrayExprNode(position pos) {
        super(pos);
    }

    @Override
    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }
}
