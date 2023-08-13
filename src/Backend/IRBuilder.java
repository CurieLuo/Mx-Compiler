package Backend;

import AST.*;
import Util.GlobalScope;

public class IRBuilder implements ASTVisitor {
    private GlobalScope gScope;

    public IRBuilder(GlobalScope gScope) {
        this.gScope = gScope;
    }

    @Override
    public void visit(RootNode it) {
    }

    @Override
    public void visit(ClassDefNode it) {
    }

    @Override
    public void visit(FuncDefNode it) {
    }

    @Override
    public void visit(ConstructorDefNode it) {
    }

    @Override
    public void visit(VarDefStmtNode it) {
    }

    @Override
    public void visit(BreakStmtNode it) {
    }

    @Override
    public void visit(ContinueStmtNode it) {
    }

    @Override
    public void visit(ReturnStmtNode it) {
    }

    @Override
    public void visit(ForStmtNode it) {
    }

    @Override
    public void visit(WhileStmtNode it) {
    }

    @Override
    public void visit(IfStmtNode it) {
    }

    @Override
    public void visit(BlockStmtNode it) {
    }

    @Override
    public void visit(ExprStmtNode it) {
    }

    @Override
    public void visit(MemberExprNode it) {
    }

    @Override
    public void visit(MemberFuncExprNode it) {
    }

    @Override
    public void visit(ArrayExprNode it) {
    }

    @Override
    public void visit(FuncExprNode it) {
    }

    @Override
    public void visit(NewExprNode it) {
    }

    @Override
    public void visit(BinaryExprNode it) {
    }

    @Override
    public void visit(UnaryExprNode it) {
    }

    @Override
    public void visit(TernaryExprNode it) {
    }

    @Override
    public void visit(AssignExprNode it) {
    }

    @Override
    public void visit(PrefixUpdateExprNode it) {
    }

    @Override
    public void visit(SuffixUpdateExprNode it) {
    }

    @Override
    public void visit(AtomExprNode it) {
    }

    @Override
    public void visit(ParamsListNode it) {
    }

    @Override
    public void visit(ArgsListNode it) {
    }
}
