package Frontend;

import AST.*;
import Util.*;
import Util.error.semanticError;

import java.util.ArrayList;

public class SemanticChecker implements ASTVisitor {

    private Scope currentScope;
    private GlobalScope gScope;

    private boolean functionReturns; //non-void function definition must have a return statement

    public SemanticChecker(GlobalScope gScope) {
        currentScope = this.gScope = gScope;
    }

//    private void print(String s) {
//        System.out.println(s);
//    }

    private void enterNewScope() {
        currentScope = new Scope(currentScope);
    }

    private void traceBack() {
        currentScope = currentScope.parentScope;
    }

    private void visitInScope(StmtNode it) {
        if (it instanceof BlockStmtNode) {
            ((BlockStmtNode) it).stmts.forEach(stmt -> stmt.accept(this));
        } else it.accept(this);
    }

    @Override
    public void visit(RootNode it) {
        boolean hasMain = false;
        for (var def : it.defs) {
            if (!hasMain && def instanceof FuncDefNode) {
                FuncDefNode funcDef = (FuncDefNode) def;
                if (funcDef.name.equals("main") && funcDef.returnType.equals(Builtins.intType) && (funcDef.params == null || funcDef.params.types.isEmpty())) {
                    hasMain = true;
                    //TODO record main; main() returns 0 by default
                }
            }

            def.accept(this);
        }
        if (!hasMain) throw new semanticError("main function not found", null);
    }

    @Override
    public void visit(ClassDefNode it) {
        currentScope = it.scope;
        // to allow forward reference of member variables except in variable initialization:
        // def non-vars, undef vars, def vars
        for (var def : it.defs) {
            if (!(def instanceof VarDefStmtNode)) {
                def.accept(this);
            }
        }

//        it.scope.removeVars();
//        for (var def : it.defs) {
//            if (def instanceof VarDefStmtNode) {
//                def.accept(this);
//            }
//        } //support member variable initialization

        currentScope = gScope;
    }

    @Override
    public void visit(FuncDefNode it) {
        enterNewScope();
//        currentScope.inFunc = true;
        currentScope.returnType = it.returnType;
        functionReturns = false;

        if (it.params != null) it.params.accept(this);
        visitInScope(it.body);
        if (!it.returnType.isVoid() && !functionReturns && !(it.name.equals("main") && currentScope.parentScope == gScope))
            throw new semanticError("non-void function has no return statement", it.pos);

        traceBack();
    }

    @Override
    public void visit(ConstructorDefNode it) {
        enterNewScope();
//        currentScope.inFunc = true;
        currentScope.returnType = Builtins.voidType;

        visitInScope(it.body);

        traceBack();
    }

    @Override
    public void visit(VarDefStmtNode it) {
        Type type = it.type;
        if (!gScope.containsType(type)) throw new semanticError("type not found", it.pos);
        for (var decl : it.vars) {
            ExprNode val = decl.val;
            if (val != null) {
                val.accept(this);
                if (!type.equals(val.type)) {
                    throw new semanticError("type mismatch in variable initialization", decl.pos);
                }
            }
            currentScope.defineVar(decl.name, type, decl.pos);
        }
    }

    @Override
    public void visit(BreakStmtNode it) {
        if (!currentScope.inLoop) throw new semanticError("break statement outside loop", it.pos);
    }

    @Override
    public void visit(ContinueStmtNode it) {
        if (!currentScope.inLoop) throw new semanticError("continue statement outside loop", it.pos);
    }

    @Override
    public void visit(ReturnStmtNode it) {
        if (currentScope.returnType == null) throw new semanticError("return statement outside function", it.pos);
        ExprNode val = it.returnVal;
        if (val != null) val.accept(this);
        if (!currentScope.returnType.equals(val == null ? Builtins.voidType : val.type))
            throw new semanticError("return type mismatch", it.pos);
        functionReturns = true;
    }

    @Override
    public void visit(ForStmtNode it) {
        enterNewScope();
        currentScope.inLoop = true;

        if (it.init != null) it.init.accept(this);
        if (it.condition != null) {
            it.condition.accept(this);
            if (!it.condition.type.isBool()) throw new semanticError("non-bool condition", it.pos);
        }
        if (it.step != null) it.step.accept(this);
        visitInScope(it.body);

        traceBack();
    }

    @Override
    public void visit(WhileStmtNode it) {
        enterNewScope();
        currentScope.inLoop = true;

        it.condition.accept(this);
        if (!it.condition.type.isBool()) throw new semanticError("non-bool condition", it.pos);
        visitInScope(it.body);

        traceBack();
    }

    @Override
    public void visit(IfStmtNode it) {
        it.condition.accept(this);
        if (!it.condition.type.isBool()) throw new semanticError("non-bool condition", it.pos);
        enterNewScope();
        visitInScope(it.trueStmt);
        traceBack();
        if (it.falseStmt != null) {
            enterNewScope();
            visitInScope(it.falseStmt);
            traceBack();
        }
    }

    @Override
    public void visit(BlockStmtNode it) {
        enterNewScope();
        visitInScope(it);
        traceBack();
    }

    @Override
    public void visit(ExprStmtNode it) {
        it.expr.accept(this);
    }

    @Override
    public void visit(MemberExprNode it) {
        it.obj.accept(this);
        if (it.obj.type.isArray()) throw new semanticError("member not found", it.pos);
        ClassScope cScope = gScope.getClass(it.obj.type.name);
        it.type = cScope.getMemberVarType(it.member);
        if (it.type == null) throw new semanticError("member not found", it.pos);
        it.assignable = it.obj.assignable || !it.obj.type.isBasicType(); // ???
    }

    @Override
    public void visit(MemberFuncExprNode it) {
        it.obj.accept(this);
        String objTypename = it.obj.type.name, funcName = it.memberFunc.name;
        it.memberFunc.cScope = it.obj.type.isArray() ?
                Builtins.arrayClassScope :
                gScope.getClass(objTypename);
        it.memberFunc.accept(this);
        it.type = it.memberFunc.type;
    }

    @Override
    public void visit(ArrayExprNode it) {
        it.obj.accept(this);
        if (!it.obj.type.isArray()) throw new semanticError("indexing non-array object", it.pos);
        it.index.accept(this);
        if (!it.index.type.isInt()) throw new semanticError("non-int index", it.pos);
        it.type = it.obj.type.copy();
        it.type.dim--;
        it.assignable = it.obj.assignable || !it.obj.type.isBasicType();// ???
    }

    @Override
    public void visit(FuncExprNode it) {
        ArrayList<Type> argTypes = new ArrayList<>();
        it.args.accept(this);
        it.args.vals.forEach(val -> argTypes.add(val.type));
        String name = it.name;
        FuncDefNode func = null;
        if (it.cScope != null) func = it.cScope.getFunc(name);
        else {
            if (currentScope.classScope != null) func = currentScope.classScope.getFunc(name);
            if (func == null) func = gScope.getFunc(name);
            //TODO simplify (duplicate names for local vars!!!!!!!!!)
        }
        // assumes params != null
        if (func == null || !argTypes.equals(func.params.types)) {
            throw new semanticError("function not found with specified arguments", it.pos);
        }
        it.type = func.returnType;
    }

    @Override
    public void visit(NewExprNode it) {
        if (it.type.isBasicType())
            throw new semanticError("new expression invalid type", it.pos);
        it.initDims.forEach(dim -> {
            dim.accept(this);
            if (!dim.type.isInt()) throw new semanticError("new expression dimension not int type", dim.pos);
        });
        // TODO constructor call
    }

    @Override
    public void visit(BinaryExprNode it) {
        it.left.accept(this);
        it.right.accept(this);
        Type lhs = it.left.type, rhs = it.right.type;
        if (!lhs.equals(rhs)) throw new semanticError("binary expression type mismatch", it.pos);
        Type type = lhs.isNull() ? rhs : lhs;
        boolean valid = switch (it.op) {
            case "-", "*", "/", "%", "<<", ">>", "&", "|", "^" -> type.isInt();
            case "&&", "||" -> type.isBool();
            case "+", "<", ">", "<=", ">=" -> type.isInt() || (lhs.isString() && rhs.isString());
            default -> true; // case "==", "!="
        };
        if (!valid) throw new semanticError("unary expression invalid type", it.pos);
        it.type = switch (it.op) {
            case "<", ">", "<=", ">=", "==", "!=" -> Builtins.boolType;
            default -> type;
        };
    }

    @Override
    public void visit(UnaryExprNode it) {
        it.obj.accept(this);
        Type type = it.obj.type;
        boolean valid = switch (it.op) {
            case "~", "-" -> type.isInt();
            case "!" -> type.isBool();
            default -> true;
        };
        if (!valid) throw new semanticError("unary expression invalid type", it.pos);
        it.type = type;
    }

    @Override
    public void visit(TernaryExprNode it) {
        it.condition.accept(this);
        if (!it.condition.type.isBool()) throw new semanticError("non-bool condition", it.pos);
        it.left.accept(this);
        it.right.accept(this);
        Type lhs = it.left.type, rhs = it.right.type;
        if (!lhs.equals(rhs)) throw new semanticError("ternary expression type mismatch", it.pos);
        it.type = lhs.isNull() ? rhs : lhs;
    }

    @Override
    public void visit(AssignExprNode it) {
        it.left.accept(this);
        if (!it.left.assignable) throw new semanticError("assigning to rvalue", it.pos);
        it.right.accept(this);
        Type lhs = it.left.type, rhs = it.right.type;
        if (!lhs.equals(rhs)) throw new semanticError("assignment type mismatch", it.pos);
        if (rhs.isNull() && lhs.isString()) throw new semanticError("null assigned to string", it.pos);
        it.type = lhs;
    }

    @Override
    public void visit(PrefixUpdateExprNode it) {
        it.obj.accept(this);
        if (!it.obj.type.isInt()) throw new semanticError("updating non-int", it.pos);
        if (!it.obj.assignable) throw new semanticError("updating rvalue", it.pos);
        it.type = it.obj.type;
    }

    @Override
    public void visit(SuffixUpdateExprNode it) {
        it.obj.accept(this);
        if (!it.obj.type.isInt()) throw new semanticError("updating non-int", it.pos);
        if (!it.obj.assignable) throw new semanticError("updating rvalue", it.pos);
        it.type = it.obj.type;
    }

    @Override
    public void visit(AtomExprNode it) {
        it.type = it.isBool ? Builtins.boolType :
                it.isInt ? Builtins.intType :
                        it.isString ? Builtins.stringType :
                                it.isNull ? Builtins.nullType :
                                        it.isThis ? currentScope.classType :
                                                currentScope.getVarType(it.content);
        if (it.type == null) {
            throw new semanticError("atom expression undefined", it.pos);
        }
    }

    @Override
    public void visit(ParamsListNode it) {
        int size = it.types.size();
        for (int i = 0; i < size; i++) {
            Type type = it.types.get(i);
            if (!gScope.containsType(type)) throw new semanticError("param type not found", it.pos);
            currentScope.defineVar(it.names.get(i), type, it.pos);
        }
    }

    @Override
    public void visit(ArgsListNode it) {
        it.vals.forEach(val -> val.accept(this));
    }
}
