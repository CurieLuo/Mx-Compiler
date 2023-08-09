package AST;

import Util.position;

public class MemberFuncExprNode extends ExprNode {
    public ExprNode obj;
    public FuncExprNode memberFunc;

    public MemberFuncExprNode(position pos) {
        super(pos);
    }

    @Override
    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }
}
