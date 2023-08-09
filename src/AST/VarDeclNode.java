package AST;

import Util.position;

public class VarDeclNode extends ASTNode {
    public String name;
    public ExprNode val;

    public VarDeclNode(position pos, String name, ExprNode val) {
        super(pos);
        this.name = name;
        this.val = val;
    }

    @Override
    public void accept(ASTVisitor visitor) {
    }
}
