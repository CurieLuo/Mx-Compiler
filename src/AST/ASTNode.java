package AST;

import Util.Scope;
import Util.position;

public abstract class ASTNode {
    public position pos;
    public Scope scope = null;

    public ASTNode(position pos) {
        this.pos = pos;
    }

    public abstract void accept(ASTVisitor visitor);
}
