package AST;

import Util.Type;

import Util.position;

import java.util.ArrayList;

public class ParamsListNode extends ASTNode {
    public ArrayList<Type> types = new ArrayList<>();
    public ArrayList<String> names = new ArrayList<>();

    public ParamsListNode(position pos) {
        super(pos);
    }

    @Override
    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }

//    @Override
//    public boolean equals(Object obj) {
//        ParamsListNode rhs = (ParamsListNode) obj;
//        return types.equals(rhs.types);
//    }
}
