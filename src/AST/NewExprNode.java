package AST;

import Util.position;

import java.util.ArrayList;

public class NewExprNode extends ExprNode {
    public ArrayList<ExprNode> initDims = new ArrayList<>();

    public NewExprNode(position pos) {
        super(pos);
        assignable = true;
    }

    @Override
    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }
}
