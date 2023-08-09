package AST;

import Util.position;
import Util.Type;

public class TypeNode extends ASTNode {
    public Type type;

    public TypeNode(position pos) {
        super(pos);
    }

    @Override
    public void accept(ASTVisitor visitor) {
    }
}
