package AST;

import Util.position;
import Util.Type;
//import MIR.entity;

abstract public class ExprNode extends ASTNode {
    public Type type;
    public boolean assignable = false;
//    public entity val;

    public ExprNode(position pos) {
        super(pos);
    }

//    public boolean isAssignable() {
//        return assignable;
//    }
}
