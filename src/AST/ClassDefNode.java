package AST;

import Util.position;
import Util.ClassScope;

import java.util.ArrayList;

public class ClassDefNode extends ASTNode implements ProgramComponent {
    public String name;
    public ArrayList<ClassComponent> defs = new ArrayList<>();

    public ClassScope scope;

    public ClassDefNode(position pos, String name) {
        super(pos);
        this.name = name;
        scope = new ClassScope(name);
        super.scope = scope;
    }

    @Override
    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }
}
