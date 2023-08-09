package AST;

import Util.position;

public class SuffixUpdateExprNode extends ExprNode {
    public ExprNode obj;

    public String op;

    public SuffixUpdateExprNode(position pos) {
        super(pos);
    }

    @Override
    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }
}
