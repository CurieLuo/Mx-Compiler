package AST;

import Util.position;

public class MemberExprNode extends ExprNode {
    public ExprNode obj;
    public String member;

    public MemberExprNode(position pos) {
        super(pos);
    }

    @Override
    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }
}
