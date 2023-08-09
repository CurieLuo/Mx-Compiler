package AST;


public interface ASTVisitor {
    void visit(RootNode it);

    void visit(ClassDefNode it);

    void visit(FuncDefNode it);

    void visit(ConstructorDefNode it);

    void visit(VarDefStmtNode it);

    void visit(BreakStmtNode it);

    void visit(ContinueStmtNode it);

    void visit(ReturnStmtNode it);

    void visit(ForStmtNode it);

    void visit(WhileStmtNode it);

    void visit(IfStmtNode it);

    void visit(BlockStmtNode it);

    void visit(ExprStmtNode it);

    void visit(MemberExprNode it);

    void visit(MemberFuncExprNode it);

    void visit(ArrayExprNode it);

    void visit(FuncExprNode it);

    void visit(NewExprNode it);

    void visit(BinaryExprNode it);

    void visit(UnaryExprNode it);

    void visit(TernaryExprNode it);

    void visit(AssignExprNode it);

    void visit(PrefixUpdateExprNode it);

    void visit(SuffixUpdateExprNode it);

    void visit(AtomExprNode it);

    void visit(ParamsListNode it);

    void visit(ArgsListNode it);
}