package Frontend;

import AST.*;
import Parser.MxParserBaseVisitor;
import Parser.MxParser;
import Util.Builtins;
import Util.Type;
import Util.error.semanticError;
import Util.position;

public class ASTBuilder extends MxParserBaseVisitor<ASTNode> {
//    private GlobalScope gScope;

    public ASTBuilder() {
//        this.gScope = gScope;
    }

//    private void print(String s) {
//        System.out.println(s);
//    }

    @Override
    public ASTNode visitProgram(MxParser.ProgramContext ctx) {
        RootNode root = new RootNode(new position(ctx));
        ctx.programComponent().forEach(def -> root.defs.add((ProgramComponent) visit(def)));
        return root;
    }

    @Override
    public ASTNode visitProgramComponent(MxParser.ProgramComponentContext ctx) {
        return visitChildren(ctx);
    }

    @Override
    public ASTNode visitClassDef(MxParser.ClassDefContext ctx) {
        ClassDefNode classDef = new ClassDefNode(new position(ctx), ctx.Identifier().getText());
        ctx.classComponent().forEach(def -> classDef.defs.add((ClassComponent) visit(def)));
        return classDef;
    }

    @Override
    public ASTNode visitClassComponent(MxParser.ClassComponentContext ctx) {
        return visitChildren(ctx);
    }

    @Override
    public ASTNode visitParamsList(MxParser.ParamsListContext ctx) {
        ParamsListNode paramsList = new ParamsListNode(new position(ctx));
        ctx.Identifier().forEach(name -> paramsList.names.add(name.getText()));
        ctx.type().forEach(type -> paramsList.types.add(((TypeNode) visit(type)).type));
        return paramsList;
    }

    @Override
    public ASTNode visitFuncDef(MxParser.FuncDefContext ctx) {
        FuncDefNode funcDef = new FuncDefNode(new position(ctx), ctx.Identifier().getText());
        funcDef.returnType = ctx.Void() != null ? Builtins.voidType : ((TypeNode) visit(ctx.type())).type;
        funcDef.body = (BlockStmtNode) visit(ctx.blockStmt());
        if (ctx.paramsList() != null) funcDef.params = (ParamsListNode) visit(ctx.paramsList());
        return funcDef;
    }

    @Override
    public ASTNode visitConstructorDef(MxParser.ConstructorDefContext ctx) {
        ConstructorDefNode constructorDef = new ConstructorDefNode(new position(ctx), ctx.Identifier().getText());
        constructorDef.body = (BlockStmtNode) visit(ctx.blockStmt());
        return constructorDef;
    }

    @Override
    public ASTNode visitBlockStmt(MxParser.BlockStmtContext ctx) {
        BlockStmtNode block = new BlockStmtNode(new position(ctx));
        ctx.stmt().forEach(stmt -> block.stmts.add((StmtNode) visit(stmt)));
        return block;
    }

    @Override
    public ASTNode visitVarDecl(MxParser.VarDeclContext ctx) {
        return new VarDeclNode(new position(ctx), ctx.Identifier().getText(), ctx.expr() == null ? null : (ExprNode) visit(ctx.expr()));
    }

    @Override
    public ASTNode visitVarDefStmt(MxParser.VarDefStmtContext ctx) {
        VarDefStmtNode varDef = new VarDefStmtNode(new position(ctx));
        varDef.type = ((TypeNode) visit(ctx.type())).type;
        ctx.varDecl().forEach(var -> varDef.vars.add((VarDeclNode) visit(var)));
        return varDef;
    }

    @Override
    public ASTNode visitExprStmt(MxParser.ExprStmtContext ctx) {
        return new ExprStmtNode(new position(ctx), (ExprNode) visit(ctx.expr()));
    }

    @Override
    public ASTNode visitIfStmt(MxParser.IfStmtContext ctx) {
        IfStmtNode ifStmt = new IfStmtNode(new position(ctx));
        ifStmt.condition = (ExprNode) visit(ctx.expr());
        ifStmt.trueStmt = (StmtNode) visit(ctx.stmt(0));
        if (ctx.stmt(1) != null) ifStmt.falseStmt = (StmtNode) visit(ctx.stmt(1));
        return ifStmt;
    }

    @Override
    public ASTNode visitWhileStmt(MxParser.WhileStmtContext ctx) {
        WhileStmtNode whileStmt = new WhileStmtNode(new position(ctx));
        whileStmt.condition = (ExprNode) visit(ctx.expr());
        whileStmt.body = (StmtNode) visit(ctx.stmt());
        return whileStmt;
    }

    @Override
    public ASTNode visitForStmt(MxParser.ForStmtContext ctx) {
        ForStmtNode forStmt = new ForStmtNode(new position(ctx));
        if (ctx.varDefStmt() != null) forStmt.init = (StmtNode) visit(ctx.varDefStmt());
        else if (ctx.exprStmt() != null) forStmt.init = (StmtNode) visit(ctx.exprStmt());
        if (ctx.condition != null) forStmt.condition = (ExprNode) visit(ctx.condition);
        if (ctx.step != null) forStmt.step = (ExprNode) visit(ctx.step);
        forStmt.body = (StmtNode) visit(ctx.stmt());
        return forStmt;
    }

    @Override
    public ASTNode visitContinueStmt(MxParser.ContinueStmtContext ctx) {
        return new ContinueStmtNode(new position(ctx));
    }

    @Override
    public ASTNode visitBreakStmt(MxParser.BreakStmtContext ctx) {
        return new BreakStmtNode(new position(ctx));
    }

    @Override
    public ASTNode visitReturnStmt(MxParser.ReturnStmtContext ctx) {
        return new ReturnStmtNode(new position(ctx), ctx.expr() == null ? null : (ExprNode) visit(ctx.expr()));
    }

    @Override
    public ASTNode visitFlowStmt(MxParser.FlowStmtContext ctx) {
        return visitChildren(ctx);
    }

    @Override
    public ASTNode visitLoopStmt(MxParser.LoopStmtContext ctx) {
        return visitChildren(ctx);
    }

    @Override
    public ASTNode visitEmptyStmt(MxParser.EmptyStmtContext ctx) {
        return new StmtNode(new position((ctx))) {
            @Override
            public void accept(ASTVisitor visitor) {
            }
        };
    }

    @Override
    public ASTNode visitStmt(MxParser.StmtContext ctx) {
        return visitChildren(ctx);
    }

    @Override
    public ASTNode visitWrapExpr(MxParser.WrapExprContext ctx) {
        return visit(ctx.expr());
    }

    @Override
    public ASTNode visitArrayExpr(MxParser.ArrayExprContext ctx) {
        ArrayExprNode arrayExpr = new ArrayExprNode(new position(ctx));
        arrayExpr.obj = (ExprNode) visit(ctx.expr(0));
        arrayExpr.index = (ExprNode) visit(ctx.expr(1));
        return arrayExpr;
    }

    @Override
    public ASTNode visitSuffixUpdateExpr(MxParser.SuffixUpdateExprContext ctx) {
        SuffixUpdateExprNode suffixUpdateExpr = new SuffixUpdateExprNode(new position(ctx));
        suffixUpdateExpr.obj = (ExprNode) visit(ctx.expr());
        suffixUpdateExpr.op = ctx.op.getText();
        return suffixUpdateExpr;
    }

    @Override
    public ASTNode visitMemberExpr(MxParser.MemberExprContext ctx) {
        MemberExprNode memberExpr = new MemberExprNode(new position(ctx));
        memberExpr.obj = (ExprNode) visit(ctx.expr());
        memberExpr.member = ctx.Identifier().getText();
        return memberExpr;
    }

    @Override
    public ASTNode visitMemberFuncExpr(MxParser.MemberFuncExprContext ctx) {
        MemberFuncExprNode memberFuncExpr = new MemberFuncExprNode(new position(ctx));
        memberFuncExpr.obj = (ExprNode) visit(ctx.expr());
        memberFuncExpr.memberFunc = (FuncExprNode) visit(ctx.funcExpr());
        return memberFuncExpr;
    }

    @Override
    public ASTNode visitBinaryExpr(MxParser.BinaryExprContext ctx) {
        BinaryExprNode binaryExpr = new BinaryExprNode(new position(ctx));
        binaryExpr.left = (ExprNode) visit(ctx.expr(0));
        binaryExpr.right = (ExprNode) visit(ctx.expr(1));
        binaryExpr.op = ctx.op.getText();
        return binaryExpr;
    }

    @Override
    public ASTNode visitPrefixUpdateExpr(MxParser.PrefixUpdateExprContext ctx) {
        PrefixUpdateExprNode prefixUpdateExpr = new PrefixUpdateExprNode(new position(ctx));
        prefixUpdateExpr.obj = (ExprNode) visit(ctx.expr());
        prefixUpdateExpr.op = ctx.op.getText();
        return prefixUpdateExpr;
    }

    @Override
    public ASTNode visitNewExpr(MxParser.NewExprContext ctx) {
        if (!ctx.redundant().isEmpty()) throw new semanticError("invalid new expression dimensions", new position(ctx));
        NewExprNode newExpr = new NewExprNode(new position(ctx));
        newExpr.type = new Type(ctx.simpleType().getText(), ctx.LeftBracket().size());
        ctx.expr().forEach(dim -> newExpr.initDims.add((ExprNode) visit(dim)));
        if (newExpr.initDims.isEmpty() && newExpr.type.isArray())
            throw new semanticError("invalid new expression dimensions", new position(ctx));
        return newExpr;
    }

    @Override
    public ASTNode visitAtomExpr(MxParser.AtomExprContext ctx) {
        return visitChildren(ctx);
    }

    @Override
    public ASTNode visitFuncCallExpr(MxParser.FuncCallExprContext ctx) {
        return visitChildren(ctx);
    }

    @Override
    public ASTNode visitUnaryExpr(MxParser.UnaryExprContext ctx) {
        UnaryExprNode unaryExpr = new UnaryExprNode(new position(ctx));
        unaryExpr.obj = (ExprNode) visit(ctx.expr());
        unaryExpr.op = ctx.op.getText();
        return unaryExpr;
    }

    @Override
    public ASTNode visitTernaryExpr(MxParser.TernaryExprContext ctx) {
        TernaryExprNode ternaryExpr = new TernaryExprNode(new position(ctx));
        ternaryExpr.condition = (ExprNode) visit(ctx.expr(0));
        ternaryExpr.left = (ExprNode) visit(ctx.expr(1));
        ternaryExpr.right = (ExprNode) visit(ctx.expr(2));
        return ternaryExpr;
    }

    @Override
    public ASTNode visitAssignExpr(MxParser.AssignExprContext ctx) {
        AssignExprNode assignExpr = new AssignExprNode(new position(ctx));
        assignExpr.left = (ExprNode) visit(ctx.expr(0));
        assignExpr.right = (ExprNode) visit(ctx.expr(1));
        return assignExpr;
    }

    @Override
    public ASTNode visitArgsList(MxParser.ArgsListContext ctx) {
        ArgsListNode argsList = new ArgsListNode(new position(ctx));
        ctx.expr().forEach(arg -> argsList.vals.add((ExprNode) visit(arg)));
        return argsList;
    }

    @Override
    public ASTNode visitFuncExpr(MxParser.FuncExprContext ctx) {
        FuncExprNode funcExpr = new FuncExprNode(new position(ctx), ctx.Identifier().getText());
        if (ctx.argsList() != null) funcExpr.args = (ArgsListNode) visit(ctx.argsList());
        return funcExpr;
    }

    @Override
    public ASTNode visitAtom(MxParser.AtomContext ctx) {
        return new AtomExprNode(new position(ctx), ctx.getText());
    }

    @Override
    public ASTNode visitType(MxParser.TypeContext ctx) {
        TypeNode type = new TypeNode(new position(ctx));
        type.type = new Type(ctx.simpleType().getText(), ctx.LeftBracket().size());
        return type;
    }
}