package AST;

import Util.Type;

import Util.position;

//import java.util.ArrayList;

public class FuncDefNode extends ASTNode implements ProgramComponent, ClassComponent {
    public String name;

    public ParamsListNode params;
    public BlockStmtNode body;

    public Type returnType;

    public FuncDefNode(position pos, String name) {
        super(pos);
        this.name = name;
        params = new ParamsListNode(pos);
//        body = new BlockStmtNode(pos); //TODO builtins
    }

    @Override
    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }
}
