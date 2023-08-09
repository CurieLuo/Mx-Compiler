package AST;

import Util.position;

public class AssignExprNode extends ExprNode {
    public ExprNode left, right;

    public AssignExprNode(position pos) {
        super(pos);
        assignable = true;
    }

    @Override
    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }
}
