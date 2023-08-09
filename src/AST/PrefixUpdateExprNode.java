package AST;

import Util.position;

public class PrefixUpdateExprNode extends ExprNode {
    public ExprNode obj;

    public String op;

    public PrefixUpdateExprNode(position pos) {
        super(pos);
        assignable = true;
    }

    @Override
    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }
}
