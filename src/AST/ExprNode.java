package AST;

import Util.position;
import Util.Type;
import MIR.Entity.Entity;

public abstract class ExprNode extends ASTNode {
    public Type type;
    public boolean assignable = false;
    public Entity val;

    public ExprNode(position pos) {
        super(pos);
    }

//    public boolean isAssignable() {
//        return assignable;
//    }
}
