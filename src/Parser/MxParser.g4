parser grammar MxParser;
options {
	tokenVocab = MxLexer;
}

program: programComponent* EOF;
programComponent: funcDef | classDef | varDefStmt;
classDef:
	Class Identifier LeftBrace classComponent* RightBrace Semicolon;
classComponent: varDefStmt | funcDef | constructorDef;
paramsList: type Identifier (Comma type Identifier)*;
funcDef: (type | Void) Identifier LeftParen paramsList? RightParen blockStmt;
constructorDef:
	Identifier LeftParen RightParen blockStmt;

blockStmt: LeftBrace stmt* RightBrace;
varDecl: Identifier (Assign expr)?;
varDefStmt: type varDecl (Comma varDecl)* Semicolon;
exprStmt: expr Semicolon;
ifStmt:
	If LeftParen expr RightParen trueStmt = stmt (
		Else falseStmt = stmt
	)?;
whileStmt: While LeftParen expr RightParen stmt;
forStmt:
	For LeftParen (varDefStmt | exprStmt| emptyStmt) condition = expr? Semicolon step = expr? RightParen stmt;
continueStmt: Continue Semicolon;
breakStmt: Break Semicolon;
returnStmt: Return expr? Semicolon;
flowStmt: continueStmt | breakStmt | returnStmt;
loopStmt: whileStmt | forStmt;
emptyStmt: Semicolon;
stmt:
	varDefStmt
	| exprStmt
	| ifStmt
	| loopStmt
	| flowStmt
	| emptyStmt
	| blockStmt;

expr:
	LeftParen expr RightParen							        # WrapExpr
	| atom												        # AtomExpr
	| funcExpr										            # FuncCallExpr
	| expr Dot funcExpr				                            # MemberFuncExpr
	| expr Dot Identifier                                       # MemberExpr
	| expr LeftBracket expr RightBracket				        # ArrayExpr
	| expr op = (Increment | Decrement)					        # SuffixUpdateExpr
	| <assoc = right> op = (Increment | Decrement) expr	        # PrefixUpdateExpr
	| <assoc = right> op = (Not | BitNot | Sub) expr	        # UnaryExpr
	| New simpleType (LeftBracket expr RightBracket)* (
		LeftBracket RightBracket
	)* redundant* (LeftParen RightParen)?							# NewExpr
	| expr op = (Mul | Div | Mod) expr								# BinaryExpr
	| expr op = (Add | Sub) expr									# BinaryExpr
	| expr op = (LeftShift | RightShift) expr						# BinaryExpr
	| expr op = (Less | Greater | LessEqual | GreaterEqual) expr	# BinaryExpr
	| expr op = (Equal | NotEqual) expr								# BinaryExpr
	| expr op = BitAnd expr											# BinaryExpr
	| expr op = BitXor expr											# BinaryExpr
	| expr op = BitOr expr											# BinaryExpr
	| expr op = And expr											# BinaryExpr
	| expr op = Or expr												# BinaryExpr
	| <assoc = right> expr Question expr Colon expr					# TernaryExpr
	| <assoc = right> expr Assign expr								# AssignExpr;

redundant: LeftBracket expr? RightBracket;
argsList: expr (Comma expr)*;
funcExpr: Identifier LeftParen argsList? RightParen;
atom:
	StringLiteral
	| IntLiteral
	| True
	| False
	| Null
	| This
	| Identifier;

simpleType: Int | Bool | String | Identifier;
type: simpleType (LeftBracket RightBracket)*;