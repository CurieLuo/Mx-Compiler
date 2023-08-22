package AST;

import IR.Entity.Entity;
import Util.ClassScope;
import Util.position;

public class FuncExprNode extends ExprNode {

    public String name;
    public ArgsListNode args;

    public ClassScope cScope = null;
    public Entity owner = null;

    public FuncExprNode(position pos, String name) {
        super(pos);
        this.name = name;
        args = new ArgsListNode(pos);
    }

    @Override
    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }
}
