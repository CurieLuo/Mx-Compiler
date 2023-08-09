package AST;

import Util.position;
import Util.Type;

import java.util.ArrayList;

public class VarDefStmtNode extends StmtNode implements ProgramComponent, ClassComponent {
    public Type type;
    public ArrayList<VarDeclNode> vars = new ArrayList<>();

    public VarDefStmtNode(position pos) {
        super(pos);
    }

    @Override
    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }
}
