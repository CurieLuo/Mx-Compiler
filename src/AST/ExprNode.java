package AST;

import Util.position;
import Util.Type;
import IR.Entity.Entity;

public abstract class ExprNode extends ASTNode {
    public Type type;
    public boolean assignable = false;
    public Entity val = null;

    public ExprNode(position pos) {
        super(pos);
    }

//    public boolean isAssignable() {
//        return assignable;
//    }
}
