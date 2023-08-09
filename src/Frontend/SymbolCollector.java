package Frontend;

import AST.*;
import Util.ClassScope;
import Util.GlobalScope;
import Util.Scope;

public class SymbolCollector implements ASTVisitor {

    private ClassScope cScope = null;
    private GlobalScope gScope;

    public SymbolCollector(GlobalScope gScope) {
        this.gScope = gScope;
    }

    @Override
    public void visit(RootNode it) {
        it.defs.forEach(def -> def.accept(this));
    }

    @Override
    public void visit(ClassDefNode it) {
        cScope = it.scope;
        cScope.parentScope = gScope;
        gScope.defineClass(it);
        it.defs.forEach(def -> def.accept(this));
        if (cScope.getConstructor() == null) {
            ConstructorDefNode defaultConstructor = new ConstructorDefNode(null, it.name);
            defaultConstructor.body = new BlockStmtNode(null);
            it.defs.add(defaultConstructor);
            defaultConstructor.accept(this);
        }
        cScope = null;
    }

    @Override
    public void visit(FuncDefNode it) {
        if (cScope != null) cScope.defineFunc(it);
        else gScope.defineFunc(it);
    }

    @Override
    public void visit(ConstructorDefNode it) {
        cScope.defineConstructor(it);
    }

    @Override
    public void visit(VarDefStmtNode it) {
        if (cScope != null) it.vars.forEach(decl -> cScope.defineVar(decl.name, it.type, decl.pos));
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
