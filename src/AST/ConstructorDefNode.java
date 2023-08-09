package AST;

//import Util.Type;

import Util.position;

import java.util.ArrayList;

public class ConstructorDefNode extends ASTNode implements ClassComponent {
    public String name;

    public BlockStmtNode body;

    public ConstructorDefNode(position pos, String name) {
        super(pos);
        this.name = name;
    }

    @Override
    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }
}
