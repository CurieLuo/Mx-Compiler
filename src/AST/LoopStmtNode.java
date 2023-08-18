package AST;

import MIR.BasicBlock;
import Util.position;

public abstract class LoopStmtNode extends StmtNode {
    public StmtNode body = null;
    public ExprNode condition = null;
    public BasicBlock condBlock = null, bodyBlock, stepBlock = null, endBlock; //TODO null

    public LoopStmtNode(position pos) {
        super(pos);
    }
}
