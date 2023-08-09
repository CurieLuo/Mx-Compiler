package Parser;

// Generated from MxParser.g4 by ANTLR 4.13.0
import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link MxParser}.
 */
public interface MxParserListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link MxParser#program}.
	 * @param ctx the parse tree
	 */
	void enterProgram(MxParser.ProgramContext ctx);
	/**
	 * Exit a parse tree produced by {@link MxParser#program}.
	 * @param ctx the parse tree
	 */
	void exitProgram(MxParser.ProgramContext ctx);
	/**
	 * Enter a parse tree produced by {@link MxParser#programComponent}.
	 * @param ctx the parse tree
	 */
	void enterProgramComponent(MxParser.ProgramComponentContext ctx);
	/**
	 * Exit a parse tree produced by {@link MxParser#programComponent}.
	 * @param ctx the parse tree
	 */
	void exitProgramComponent(MxParser.ProgramComponentContext ctx);
	/**
	 * Enter a parse tree produced by {@link MxParser#classDef}.
	 * @param ctx the parse tree
	 */
	void enterClassDef(MxParser.ClassDefContext ctx);
	/**
	 * Exit a parse tree produced by {@link MxParser#classDef}.
	 * @param ctx the parse tree
	 */
	void exitClassDef(MxParser.ClassDefContext ctx);
	/**
	 * Enter a parse tree produced by {@link MxParser#classComponent}.
	 * @param ctx the parse tree
	 */
	void enterClassComponent(MxParser.ClassComponentContext ctx);
	/**
	 * Exit a parse tree produced by {@link MxParser#classComponent}.
	 * @param ctx the parse tree
	 */
	void exitClassComponent(MxParser.ClassComponentContext ctx);
	/**
	 * Enter a parse tree produced by {@link MxParser#paramsList}.
	 * @param ctx the parse tree
	 */
	void enterParamsList(MxParser.ParamsListContext ctx);
	/**
	 * Exit a parse tree produced by {@link MxParser#paramsList}.
	 * @param ctx the parse tree
	 */
	void exitParamsList(MxParser.ParamsListContext ctx);
	/**
	 * Enter a parse tree produced by {@link MxParser#funcDef}.
	 * @param ctx the parse tree
	 */
	void enterFuncDef(MxParser.FuncDefContext ctx);
	/**
	 * Exit a parse tree produced by {@link MxParser#funcDef}.
	 * @param ctx the parse tree
	 */
	void exitFuncDef(MxParser.FuncDefContext ctx);
	/**
	 * Enter a parse tree produced by {@link MxParser#constructorDef}.
	 * @param ctx the parse tree
	 */
	void enterConstructorDef(MxParser.ConstructorDefContext ctx);
	/**
	 * Exit a parse tree produced by {@link MxParser#constructorDef}.
	 * @param ctx the parse tree
	 */
	void exitConstructorDef(MxParser.ConstructorDefContext ctx);
	/**
	 * Enter a parse tree produced by {@link MxParser#blockStmt}.
	 * @param ctx the parse tree
	 */
	void enterBlockStmt(MxParser.BlockStmtContext ctx);
	/**
	 * Exit a parse tree produced by {@link MxParser#blockStmt}.
	 * @param ctx the parse tree
	 */
	void exitBlockStmt(MxParser.BlockStmtContext ctx);
	/**
	 * Enter a parse tree produced by {@link MxParser#varDecl}.
	 * @param ctx the parse tree
	 */
	void enterVarDecl(MxParser.VarDeclContext ctx);
	/**
	 * Exit a parse tree produced by {@link MxParser#varDecl}.
	 * @param ctx the parse tree
	 */
	void exitVarDecl(MxParser.VarDeclContext ctx);
	/**
	 * Enter a parse tree produced by {@link MxParser#varDefStmt}.
	 * @param ctx the parse tree
	 */
	void enterVarDefStmt(MxParser.VarDefStmtContext ctx);
	/**
	 * Exit a parse tree produced by {@link MxParser#varDefStmt}.
	 * @param ctx the parse tree
	 */
	void exitVarDefStmt(MxParser.VarDefStmtContext ctx);
	/**
	 * Enter a parse tree produced by {@link MxParser#exprStmt}.
	 * @param ctx the parse tree
	 */
	void enterExprStmt(MxParser.ExprStmtContext ctx);
	/**
	 * Exit a parse tree produced by {@link MxParser#exprStmt}.
	 * @param ctx the parse tree
	 */
	void exitExprStmt(MxParser.ExprStmtContext ctx);
	/**
	 * Enter a parse tree produced by {@link MxParser#ifStmt}.
	 * @param ctx the parse tree
	 */
	void enterIfStmt(MxParser.IfStmtContext ctx);
	/**
	 * Exit a parse tree produced by {@link MxParser#ifStmt}.
	 * @param ctx the parse tree
	 */
	void exitIfStmt(MxParser.IfStmtContext ctx);
	/**
	 * Enter a parse tree produced by {@link MxParser#whileStmt}.
	 * @param ctx the parse tree
	 */
	void enterWhileStmt(MxParser.WhileStmtContext ctx);
	/**
	 * Exit a parse tree produced by {@link MxParser#whileStmt}.
	 * @param ctx the parse tree
	 */
	void exitWhileStmt(MxParser.WhileStmtContext ctx);
	/**
	 * Enter a parse tree produced by {@link MxParser#forStmt}.
	 * @param ctx the parse tree
	 */
	void enterForStmt(MxParser.ForStmtContext ctx);
	/**
	 * Exit a parse tree produced by {@link MxParser#forStmt}.
	 * @param ctx the parse tree
	 */
	void exitForStmt(MxParser.ForStmtContext ctx);
	/**
	 * Enter a parse tree produced by {@link MxParser#continueStmt}.
	 * @param ctx the parse tree
	 */
	void enterContinueStmt(MxParser.ContinueStmtContext ctx);
	/**
	 * Exit a parse tree produced by {@link MxParser#continueStmt}.
	 * @param ctx the parse tree
	 */
	void exitContinueStmt(MxParser.ContinueStmtContext ctx);
	/**
	 * Enter a parse tree produced by {@link MxParser#breakStmt}.
	 * @param ctx the parse tree
	 */
	void enterBreakStmt(MxParser.BreakStmtContext ctx);
	/**
	 * Exit a parse tree produced by {@link MxParser#breakStmt}.
	 * @param ctx the parse tree
	 */
	void exitBreakStmt(MxParser.BreakStmtContext ctx);
	/**
	 * Enter a parse tree produced by {@link MxParser#returnStmt}.
	 * @param ctx the parse tree
	 */
	void enterReturnStmt(MxParser.ReturnStmtContext ctx);
	/**
	 * Exit a parse tree produced by {@link MxParser#returnStmt}.
	 * @param ctx the parse tree
	 */
	void exitReturnStmt(MxParser.ReturnStmtContext ctx);
	/**
	 * Enter a parse tree produced by {@link MxParser#flowStmt}.
	 * @param ctx the parse tree
	 */
	void enterFlowStmt(MxParser.FlowStmtContext ctx);
	/**
	 * Exit a parse tree produced by {@link MxParser#flowStmt}.
	 * @param ctx the parse tree
	 */
	void exitFlowStmt(MxParser.FlowStmtContext ctx);
	/**
	 * Enter a parse tree produced by {@link MxParser#loopStmt}.
	 * @param ctx the parse tree
	 */
	void enterLoopStmt(MxParser.LoopStmtContext ctx);
	/**
	 * Exit a parse tree produced by {@link MxParser#loopStmt}.
	 * @param ctx the parse tree
	 */
	void exitLoopStmt(MxParser.LoopStmtContext ctx);
	/**
	 * Enter a parse tree produced by {@link MxParser#emptyStmt}.
	 * @param ctx the parse tree
	 */
	void enterEmptyStmt(MxParser.EmptyStmtContext ctx);
	/**
	 * Exit a parse tree produced by {@link MxParser#emptyStmt}.
	 * @param ctx the parse tree
	 */
	void exitEmptyStmt(MxParser.EmptyStmtContext ctx);
	/**
	 * Enter a parse tree produced by {@link MxParser#stmt}.
	 * @param ctx the parse tree
	 */
	void enterStmt(MxParser.StmtContext ctx);
	/**
	 * Exit a parse tree produced by {@link MxParser#stmt}.
	 * @param ctx the parse tree
	 */
	void exitStmt(MxParser.StmtContext ctx);
	/**
	 * Enter a parse tree produced by the {@code WrapExpr}
	 * labeled alternative in {@link MxParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterWrapExpr(MxParser.WrapExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code WrapExpr}
	 * labeled alternative in {@link MxParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitWrapExpr(MxParser.WrapExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code BinaryExpr}
	 * labeled alternative in {@link MxParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterBinaryExpr(MxParser.BinaryExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code BinaryExpr}
	 * labeled alternative in {@link MxParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitBinaryExpr(MxParser.BinaryExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code PrefixUpdateExpr}
	 * labeled alternative in {@link MxParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterPrefixUpdateExpr(MxParser.PrefixUpdateExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code PrefixUpdateExpr}
	 * labeled alternative in {@link MxParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitPrefixUpdateExpr(MxParser.PrefixUpdateExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code AtomExpr}
	 * labeled alternative in {@link MxParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterAtomExpr(MxParser.AtomExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code AtomExpr}
	 * labeled alternative in {@link MxParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitAtomExpr(MxParser.AtomExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code UnaryExpr}
	 * labeled alternative in {@link MxParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterUnaryExpr(MxParser.UnaryExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code UnaryExpr}
	 * labeled alternative in {@link MxParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitUnaryExpr(MxParser.UnaryExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code MemberFuncExpr}
	 * labeled alternative in {@link MxParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterMemberFuncExpr(MxParser.MemberFuncExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code MemberFuncExpr}
	 * labeled alternative in {@link MxParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitMemberFuncExpr(MxParser.MemberFuncExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code AssignExpr}
	 * labeled alternative in {@link MxParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterAssignExpr(MxParser.AssignExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code AssignExpr}
	 * labeled alternative in {@link MxParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitAssignExpr(MxParser.AssignExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code ArrayExpr}
	 * labeled alternative in {@link MxParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterArrayExpr(MxParser.ArrayExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code ArrayExpr}
	 * labeled alternative in {@link MxParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitArrayExpr(MxParser.ArrayExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code SuffixUpdateExpr}
	 * labeled alternative in {@link MxParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterSuffixUpdateExpr(MxParser.SuffixUpdateExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code SuffixUpdateExpr}
	 * labeled alternative in {@link MxParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitSuffixUpdateExpr(MxParser.SuffixUpdateExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code MemberExpr}
	 * labeled alternative in {@link MxParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterMemberExpr(MxParser.MemberExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code MemberExpr}
	 * labeled alternative in {@link MxParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitMemberExpr(MxParser.MemberExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code NewExpr}
	 * labeled alternative in {@link MxParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterNewExpr(MxParser.NewExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code NewExpr}
	 * labeled alternative in {@link MxParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitNewExpr(MxParser.NewExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code TernaryExpr}
	 * labeled alternative in {@link MxParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterTernaryExpr(MxParser.TernaryExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code TernaryExpr}
	 * labeled alternative in {@link MxParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitTernaryExpr(MxParser.TernaryExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code FuncCallExpr}
	 * labeled alternative in {@link MxParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterFuncCallExpr(MxParser.FuncCallExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code FuncCallExpr}
	 * labeled alternative in {@link MxParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitFuncCallExpr(MxParser.FuncCallExprContext ctx);
	/**
	 * Enter a parse tree produced by {@link MxParser#redundant}.
	 * @param ctx the parse tree
	 */
	void enterRedundant(MxParser.RedundantContext ctx);
	/**
	 * Exit a parse tree produced by {@link MxParser#redundant}.
	 * @param ctx the parse tree
	 */
	void exitRedundant(MxParser.RedundantContext ctx);
	/**
	 * Enter a parse tree produced by {@link MxParser#argsList}.
	 * @param ctx the parse tree
	 */
	void enterArgsList(MxParser.ArgsListContext ctx);
	/**
	 * Exit a parse tree produced by {@link MxParser#argsList}.
	 * @param ctx the parse tree
	 */
	void exitArgsList(MxParser.ArgsListContext ctx);
	/**
	 * Enter a parse tree produced by {@link MxParser#funcExpr}.
	 * @param ctx the parse tree
	 */
	void enterFuncExpr(MxParser.FuncExprContext ctx);
	/**
	 * Exit a parse tree produced by {@link MxParser#funcExpr}.
	 * @param ctx the parse tree
	 */
	void exitFuncExpr(MxParser.FuncExprContext ctx);
	/**
	 * Enter a parse tree produced by {@link MxParser#atom}.
	 * @param ctx the parse tree
	 */
	void enterAtom(MxParser.AtomContext ctx);
	/**
	 * Exit a parse tree produced by {@link MxParser#atom}.
	 * @param ctx the parse tree
	 */
	void exitAtom(MxParser.AtomContext ctx);
	/**
	 * Enter a parse tree produced by {@link MxParser#simpleType}.
	 * @param ctx the parse tree
	 */
	void enterSimpleType(MxParser.SimpleTypeContext ctx);
	/**
	 * Exit a parse tree produced by {@link MxParser#simpleType}.
	 * @param ctx the parse tree
	 */
	void exitSimpleType(MxParser.SimpleTypeContext ctx);
	/**
	 * Enter a parse tree produced by {@link MxParser#type}.
	 * @param ctx the parse tree
	 */
	void enterType(MxParser.TypeContext ctx);
	/**
	 * Exit a parse tree produced by {@link MxParser#type}.
	 * @param ctx the parse tree
	 */
	void exitType(MxParser.TypeContext ctx);
}