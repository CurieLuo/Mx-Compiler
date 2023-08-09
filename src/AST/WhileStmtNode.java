package AST;

import Util.position;

public class WhileStmtNode extends LoopStmtNode {
    public WhileStmtNode(position pos) {
        super(pos);
    }

    @Override
    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }
}
