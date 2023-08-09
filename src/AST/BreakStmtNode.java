package AST;

import Util.position;

public class BreakStmtNode extends FlowStmtNode {
    public BreakStmtNode(position pos) {
        super(pos);
    }

    @Override
    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }
}