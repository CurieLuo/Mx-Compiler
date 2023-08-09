package AST;

//import Util.Type;

import Util.position;

import java.util.ArrayList;

public class ArgsListNode extends ASTNode {
    public ArrayList<ExprNode> vals = new ArrayList<>();

    public ArgsListNode(position pos) {
        super(pos);
    }

    @Override
    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }
}
