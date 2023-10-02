package Frontend;

import AST.*;
import IR.*;
import IR.Entity.*;
import IR.Inst.*;
import IR.Type.IRPtrType;
import IR.Type.IRStructType;
import IR.Type.IRType;
import Util.*;

import static Util.Builtins.*;

import java.util.HashMap;
import java.util.LinkedList;

public class IRBuilder implements ASTVisitor {
    private final GlobalScope gScope;
    private Scope currentScope;

    private IRStructType currentStruct = null;
    private IRFunction currentFunc = null;
    private IRBasicBlock currentBlock = null;
    private final IRProgram program;

    private boolean isDeclared = false, isBuiltin;

    private final HashMap<String, IRStructType> structs = new HashMap<>();
    private final HashMap<String, IRFunction> funcs = new HashMap<>();

    public IRBuilder(GlobalScope gScope, IRProgram program) {
        currentScope = this.gScope = gScope;
        this.program = program;
    }

    private void enterNewScope(ASTNode it) {
        currentScope = it.scope;
    }

    private void traceBack() {
        currentScope = currentScope.parentScope;
    }

    private void visitInScope(StmtNode it) {
        if (it instanceof BlockStmtNode block) {
            for (var stmt : block.stmts) {
                stmt.accept(this);
            }
        } else it.accept(this);
    }

    private IRType toIRType(Type type) {
        IRType ret = switch (type.name) {
            case "int" -> irIntType;
            case "bool" -> irBoolType;
            case "string" -> irStringType;
            case "void" -> irVoidType;
            case "null" -> irNullType;
            default -> new IRPtrType(structs.get(type.name));
        };
        if (type.isArray()) ret = new IRPtrType(ret, type.dim);
        return ret;
    }

    private void toRValue(ExprNode it) {
        if (it.assignable) {
            IRRegister val = new IRRegister(((IRPtrType) it.val.type).pointToType());
            currentBlock.addInst(new IRLoadInst(val, (IRRegister) it.val));
            it.val = val;
        }
    }

    private void toRValue(ExprNode it, IRType type) {
        if (it.type.isNull()) it.val.type = type;
        else toRValue(it);
    }

    private void visitAll(ASTNode... nodes) {
        for (var it : nodes) it.accept(this);
    }

    @Override
    public void visit(RootNode it) {
        for (var def : it.defs)
            if (def instanceof ClassDefNode classDef) {
                IRStructType struct = program.addStruct(classDef.name);
                structs.put(classDef.name, struct);
            }

        for (var def : it.defs) {
            def.accept(this);
        } // declare

        isBuiltin = true;
        visitAll(stringClass, print, println, printInt, printlnInt, getString, getInt, toString);
        isBuiltin = false;

        isDeclared = true;
        currentFunc = program.initFunc;
        currentBlock = currentFunc.entryBlock();
        for (var def : it.defs) {
            if (def instanceof VarDefStmtNode) def.accept(this);
        }
        if (currentBlock.terminatorInst == null) currentBlock.setJump(currentFunc.returnBlock());

        for (var def : it.defs) {
            if (!(def instanceof VarDefStmtNode)) {
                def.accept(this);
            }
        }
    }

    @Override
    public void visit(ClassDefNode it) {
        enterNewScope(it);
        if (!isBuiltin) currentStruct = structs.get(it.name);

        for (var def : it.defs) {
            if (!(isDeclared && def instanceof VarDefStmtNode)) {
                def.accept(this);
            }
        }

        currentStruct = null;
        traceBack();
    }

    @Override
    public void visit(FuncDefNode it) {
        if (!isBuiltin) enterNewScope(it);

        boolean inClass = currentScope.classScope != null;
        String name = (inClass ? (currentScope.classScope.name + ".") : "") + it.name;
        if (!isDeclared) {
            IRType irType = toIRType(it.returnType);
            IRFunction func = currentFunc = program.addFunc(name, irType, isBuiltin);
            funcs.put(name, func);
            currentBlock = func.entryBlock();
            if (inClass) {
                addParam("this", toIRType(currentScope.classType), 0);
            }
            it.params.accept(this);
        } else {
            IRRegister.reset(1);
            IRBasicBlock.reset();
            IRFunction func = currentFunc = funcs.get(name);
            currentBlock = func.entryBlock();
            IRType irType = func.returnType;

            if (func == program.mainFunc) currentBlock.addInst(new IRCallInst(null, program.initFunc.name));
            // should use a flag to prevent repeated calls (if main() is recursive)

            IRRegister retval = null;
            if (!it.returnType.isVoid()) {
                retval = new IRRegister("ret.val", new IRPtrType(irType));
                currentScope.addEntity(retval);
                currentBlock.addInst(new IRAllocaInst(retval));
            }

            visitInScope(it.body);

            if (currentBlock.terminatorInst == null) {
                if (func == program.mainFunc) {
                    currentBlock.addInst(new IRStoreInst(irInt0, retval));
                }
                currentBlock.setJump(currentFunc.returnBlock());
            }

            currentBlock = func.returnBlock();
            if (!it.returnType.isVoid()) {
                IRRegister ret = new IRRegister(irType);
                currentBlock.addInst(new IRLoadInst(ret, retval));
                currentBlock.addInst(new IRRetInst(ret));
            } else {
                currentBlock.addInst(new IRRetInst(Builtins.irVoid));
            }

        }

        currentFunc = null;
        currentBlock = null;
        if (!isBuiltin) traceBack();
    }

    @Override
    public void visit(ConstructorDefNode it) {
        enterNewScope(it);

        String name = it.name + "." + it.name;
        if (!isDeclared) {
            IRFunction func = currentFunc = program.addFunc(name, Builtins.irVoidType, false);
            funcs.put(name, func);
            currentBlock = func.entryBlock();
            addParam("this", new IRPtrType(structs.get(currentScope.classScope.name)), 0);
        } else {
            IRRegister.reset(1);
            IRBasicBlock.reset();
            IRFunction func = currentFunc = funcs.get(name);
            currentBlock = func.entryBlock();

            visitInScope(it.body);
            if (currentBlock.terminatorInst == null) {
                currentBlock.setJump(currentFunc.returnBlock());
            }

            currentBlock = func.returnBlock();
            currentBlock.addInst(new IRRetInst(Builtins.irVoid));

        }

        currentFunc = null;
        currentBlock = null;
        traceBack();
    }

    @Override
    public void visit(VarDefStmtNode it) {
        IRType irType = toIRType(it.type);
        if (currentScope instanceof ClassScope) {
            it.vars.forEach(decl -> currentStruct.addMember(decl.name, irType));
            return;
        } // member variable
        boolean isGlobal = currentScope == gScope;
        for (var decl : it.vars) {
            String name = decl.name;
            if (isGlobal) {
                if (!isDeclared) {
                    IRGlobalVar var = program.addGlobalVar(name, irType);
                    gScope.addEntity(var);
                } else if (decl.val != null) {
                    IRGlobalVar var = (IRGlobalVar) gScope.getEntity(name);
                    decl.val.accept(this);
                    toRValue(decl.val);
                    IREntity val = decl.val.val;
                    if (val instanceof IRConst constVal && !(constVal instanceof IRStringConst)) {
                        var.initVal = constVal;
                    } else {
                        currentBlock.addInst(new IRStoreInst(val, var));
                    }
                }
            } else {
                IRRegister var = new IRRegister(name, new IRPtrType(irType));
                currentScope.addEntity(var);
                currentBlock.addInst(new IRAllocaInst(var));
                if (decl.val != null) {
                    decl.val.accept(this);
                    toRValue(decl.val);
                    IREntity val = decl.val.val;
                    currentBlock.addInst(new IRStoreInst(val, var));
                }
            }
        }
    }

    @Override
    public void visit(BreakStmtNode it) {
        currentBlock.setJump(currentScope.loop.endBlock);
    }

    @Override
    public void visit(ContinueStmtNode it) {
        LoopStmtNode loop = currentScope.loop;
        currentBlock.setJump(loop.stepBlock != null ? loop.stepBlock :
                loop.condBlock != null ? loop.condBlock : loop.bodyBlock);
    }

    @Override
    public void visit(ReturnStmtNode it) {
        if (it.returnVal != null) {
            it.returnVal.accept(this);
            toRValue(it.returnVal, currentFunc.returnType);
            currentBlock.addInst(new IRStoreInst(it.returnVal.val, currentScope.getEntity("ret.val")));
        }
        currentBlock.addInst(new IRJumpInst(currentFunc.returnBlock()));
    }

    @Override
    public void visit(ForStmtNode it) {
        enterNewScope(it);

        IRBasicBlock condBlock = it.condition != null ? currentFunc.addBlock("for.cond") : null,
                bodyBlock = currentFunc.addBlock("for.body"),
                stepBlock = it.step != null ? currentFunc.addBlock("for.step") : null,
                endBlock = currentFunc.addBlock("for.end");
        it.condBlock = condBlock;
        it.bodyBlock = bodyBlock;
        it.stepBlock = stepBlock;
        it.endBlock = endBlock;

        if (it.init != null) {
            it.init.accept(this);
        }
        currentBlock.setJump(condBlock != null ? condBlock : bodyBlock);

        if (condBlock != null) {
            currentBlock = condBlock;
            it.condition.accept(this);
            toRValue(it.condition);
            currentBlock.addInst(new IRBranchInst(it.condition.val, bodyBlock, endBlock));
        }

        if (stepBlock != null) {
            currentBlock = stepBlock;
            it.step.accept(this);
            currentBlock.setJump(condBlock != null ? condBlock : bodyBlock);
        }

        currentBlock = bodyBlock;
        visitInScope(it.body);
        currentBlock.setJump(stepBlock != null ? stepBlock : condBlock != null ? condBlock : bodyBlock);

        currentBlock = endBlock;

        traceBack();
    }

    @Override
    public void visit(WhileStmtNode it) {
        enterNewScope(it);

        IRBasicBlock condBlock = currentFunc.addBlock("while.cond"),
                bodyBlock = currentFunc.addBlock("while.body"),
                endBlock = currentFunc.addBlock("while.end");
        it.condBlock = condBlock;
        it.bodyBlock = bodyBlock;
        it.endBlock = endBlock;

        currentBlock.setJump(condBlock);
        currentBlock = condBlock;
        it.condition.accept(this);
        toRValue(it.condition);
        currentBlock.addInst(new IRBranchInst(it.condition.val, bodyBlock, endBlock));

        currentBlock = bodyBlock;
        visitInScope(it.body);
        currentBlock.setJump(condBlock);

        currentBlock = endBlock;

        traceBack();
    }

    @Override
    public void visit(IfStmtNode it) {
        IRBasicBlock
                trueBlock = currentFunc.addBlock("if.true"),
                falseBlock = it.falseStmt != null ? currentFunc.addBlock("if.false") : null,
                endBlock = currentFunc.addBlock("if.end");

        it.condition.accept(this);
        toRValue(it.condition);
        currentBlock.addInst(new IRBranchInst(it.condition.val, trueBlock, falseBlock != null ? falseBlock : endBlock));

        currentBlock = trueBlock;
        enterNewScope(it.trueStmt);
        visitInScope(it.trueStmt);
        traceBack();
        currentBlock.setJump(endBlock);

        if (falseBlock != null) {
            currentBlock = falseBlock;
            enterNewScope(it.falseStmt);
            visitInScope(it.falseStmt);
            traceBack();
            currentBlock.setJump(endBlock);
        }

        currentBlock = endBlock;
    }

    @Override
    public void visit(BlockStmtNode it) {
        enterNewScope(it);
        visitInScope(it);
        traceBack();
    }

    @Override
    public void visit(ExprStmtNode it) {
        it.expr.accept(this);
    }

    IRRegister visitMember(IREntity owner, String name) {
        IRStructType struct = (IRStructType) ((IRPtrType) owner.type).pointToType();
        int index = struct.getIndex(name);
        IRType type = struct.memberTypes.get(index);
        IRRegister reg = new IRRegister(new IRPtrType(type));
        currentBlock.addInst(new IRGetElementPtrInst(reg, owner, irInt0, new IRIntConst(index)));
        return reg;
    }

    @Override
    public void visit(MemberExprNode it) {
        it.obj.accept(this);
        toRValue(it.obj);
        it.val = visitMember(it.obj.val, it.member);
    }

    @Override
    public void visit(MemberFuncExprNode it) {
        it.obj.accept(this);
        toRValue(it.obj);
        if (it.obj.type.isArray()) {
            // mx.array.size()
            IRRegister reg = new IRRegister(irIntType);
            it.val = reg;
            IRRegister addr = new IRRegister(irIntPtrType);
            it.obj.val.type = irIntPtrType;
            currentBlock.addInst(new IRGetElementPtrInst(addr, it.obj.val, irIntNegative1));
            currentBlock.addInst(new IRLoadInst(reg, addr));
        } else {
            it.memberFunc.owner = it.obj.val;
            it.memberFunc.accept(this);
            it.val = it.memberFunc.val;
        }
    }

    @Override
    public void visit(ArrayExprNode it) {
        it.obj.accept(this);
        toRValue(it.obj);
        it.index.accept(this);
        toRValue(it.index);
        IRRegister reg = new IRRegister(it.obj.val.type);
        it.val = reg;
        currentBlock.addInst(new IRGetElementPtrInst(reg, it.obj.val, it.index.val));
    }

    @Override
    public void visit(FuncExprNode it) {
        it.args.accept(this);
        boolean isMemberFunc = it.cScope != null;
        String name = (isMemberFunc ? it.cScope.name + "." : "") + it.name;
        IRFunction func = funcs.get(name);
        IRRegister reg = it.type.isVoid() ? null : new IRRegister(func.returnType);
        it.val = reg;
        IRCallInst call = new IRCallInst(reg, name);
        if (isMemberFunc) call.args.add(it.owner != null ? it.owner : currentScope.getEntity("this"));
        var args = it.args.vals;
        for (int i = 0; i < args.size(); i++) {
            var arg = args.get(i);
            toRValue(arg, func.params.get(i + (isMemberFunc ? 1 : 0)).type);
            call.addArg(arg.val);
        }
        currentBlock.addInst(call);
    }

    public IRRegister newArray(IRPtrType type, LinkedList<IREntity> initDims) {
        IRType valType = type.pointToType();
        IRRegister reg = new IRRegister(type);
        IREntity allocSize;
        IREntity length = initDims.pollFirst();
        if (length instanceof IRIntConst k) {
            allocSize = new IRIntConst(k.val * valType.size + 4);
        } else {
            IRRegister actualSize = new IRRegister(irIntType);
            currentBlock.addInst(new IRBinaryInst(actualSize, "mul", length, new IRIntConst(valType.size)));
            allocSize = new IRRegister(irIntType);
            currentBlock.addInst(new IRBinaryInst((IRRegister) allocSize, "add", actualSize, irInt4));
        }
        IRRegister allocBegin = new IRRegister(irIntPtrType);
        currentBlock.addInst(new IRCallInst(allocBegin, "malloc", allocSize));
        currentBlock.addInst(new IRGetElementPtrInst(reg, allocBegin, irInt1));
        currentBlock.addInst(new IRStoreInst(length, allocBegin));

        if (initDims.isEmpty()) return reg;

//        pseudocode: for (int i=0; i < length; i++) reg[i] = newArray(valType, initDims)

        IRBasicBlock condBlock = currentFunc.addBlock("new.cond"),
                bodyBlock = currentFunc.addBlock("new.body"),
                stepBlock = currentFunc.addBlock("new.step"),
                endBlock = currentFunc.addBlock("new.end");

//         you can also create new ASTNodes and visit them

        IRRegister iterVar = new IRRegister(irIntPtrType);
        currentBlock.addInst(new IRAllocaInst(iterVar));
        currentBlock.addInst(new IRStoreInst(irInt0, iterVar));

        currentBlock.setJump(condBlock);
        currentBlock = condBlock;
        IRRegister condition = new IRRegister(irBoolType);
        IRRegister i = new IRRegister(irIntType);
        currentBlock.addInst(new IRLoadInst(i, iterVar));
        currentBlock.addInst(new IRIcmpInst(condition, "slt", i, length));
        currentBlock.addInst(new IRBranchInst(condition, bodyBlock, endBlock));

        currentBlock = bodyBlock;
        i = new IRRegister(irIntType);
        currentBlock.addInst(new IRLoadInst(i, iterVar));
        IRRegister addr = new IRRegister(type);
        currentBlock.addInst(new IRGetElementPtrInst(addr, reg, i));
        IRRegister val = newArray((IRPtrType) valType, initDims);
        currentBlock.addInst(new IRStoreInst(val, addr));
        currentBlock.setJump(stepBlock);

        currentBlock = stepBlock;
        i = new IRRegister(irIntType);
        currentBlock.addInst(new IRLoadInst(i, iterVar));
        IRRegister iNext = new IRRegister(irIntType);
        currentBlock.addInst(new IRBinaryInst(iNext, "add", i, irInt1));
        currentBlock.addInst(new IRStoreInst(iNext, iterVar));
        currentBlock.setJump(condBlock);

        currentBlock = endBlock;

        return reg;
    }

    @Override
    public void visit(NewExprNode it) {

        IRPtrType type = (IRPtrType) toIRType(it.type);

        if (!it.type.isArray()) {
            IRRegister reg = new IRRegister(type);
            it.val = reg;
            currentBlock.addInst(new IRCallInst(reg, "malloc",
                    new IRIntConst(type.pointToType().size)));
            String constructor = it.type.name + "." + it.type.name;
            if (funcs.containsKey(constructor)) currentBlock.addInst(new IRCallInst(null, constructor, reg));
            return;
        }

        LinkedList<IREntity> initDims = new LinkedList<>();
        for (var dim : it.initDims) {
            dim.accept(this);
            toRValue(dim);
            initDims.add(dim.val);
        }
        it.val = newArray(type, initDims);
    }

    @Override
    public void visit(BinaryExprNode it) {
        IRType type = toIRType(it.type);
        it.left.accept(this);
        if (it.op.equals("&&") || it.op.equals("||")) {
            toRValue(it.left);
            boolean isAnd = it.op.equals("&&");
            if (it.left.val instanceof IRBoolConst a) {
                if ((a.val && !isAnd) || (!a.val && isAnd)) {
                    it.val = IRBoolConst.instance(a.val);
                    return;
                }
            }

            IRRegister reg = new IRRegister(irBoolType);
            it.val = reg;
            IRPhiInst phiInst = new IRPhiInst(reg);
            phiInst.addSource(currentBlock, it.left.val);

            String label = "l" + (isAnd ? "and" : "or");
            IRBasicBlock rhsBlock = currentFunc.addBlock(label + ".rhs"),
                    endBlock = currentFunc.addBlock(label + ".end");
            currentBlock.addInst(isAnd ? new IRBranchInst(it.left.val, rhsBlock, endBlock) :
                    new IRBranchInst(it.left.val, endBlock, rhsBlock));

            currentBlock = rhsBlock;
            it.right.accept(this);
            toRValue(it.right);
            currentBlock.setJump(endBlock);
            phiInst.addSource(currentBlock, it.right.val);

            currentBlock = endBlock;
            if (it.right.val instanceof IRBoolConst b) {
                it.val = IRBoolConst.instance(b.val);
                return;
            }

            endBlock.addInst(phiInst);
            return;
        }

        it.right.accept(this);
        IRType operandType = it.left.type.isNull() ? it.right.val.type : it.left.val.type;
        toRValue(it.left, operandType);
        toRValue(it.right, operandType);

        IREntity lhs = it.left.val, rhs = it.right.val;
        boolean isCmp = false;
        String op = null;
        switch (it.op) {
            case "*" -> {
                op = "mul";
                if (lhs instanceof IRIntConst a && rhs instanceof IRIntConst b) {
                    it.val = new IRIntConst(a.val * b.val);
                }
            }
            case "/" -> {
                op = "sdiv";
                if (lhs instanceof IRIntConst a && rhs instanceof IRIntConst b) {
                    it.val = new IRIntConst(a.val / b.val);
                }
            }
            case "%" -> {
                op = "srem";
                if (lhs instanceof IRIntConst a && rhs instanceof IRIntConst b) {
                    it.val = new IRIntConst(a.val % b.val);
                }
            }
            case "+" -> {
                op = "add";
                if (lhs instanceof IRIntConst a && rhs instanceof IRIntConst b) {
                    it.val = new IRIntConst(a.val + b.val);
                }
            }
            case "-" -> {
                op = "sub";
                if (lhs instanceof IRIntConst a && rhs instanceof IRIntConst b) {
                    it.val = new IRIntConst(a.val - b.val);
                }
            }
            case "<<" -> {
                op = "shl";
                if (lhs instanceof IRIntConst a && rhs instanceof IRIntConst b) {
                    it.val = new IRIntConst(a.val << b.val);
                }
            }
            case ">>" -> {
                op = "ashr";
                if (lhs instanceof IRIntConst a && rhs instanceof IRIntConst b) {
                    it.val = new IRIntConst(a.val >> b.val);
                }
            }
            case "&" -> {
                op = "and";
                if (lhs instanceof IRIntConst a && rhs instanceof IRIntConst b) {
                    it.val = new IRIntConst(a.val & b.val);
                }
            }
            case "^" -> {
                op = "xor";
                if (lhs instanceof IRIntConst a && rhs instanceof IRIntConst b) {
                    it.val = new IRIntConst(a.val ^ b.val);
                }
            }
            case "<" -> {
                isCmp = true;
                op = "slt";
                if (lhs instanceof IRIntConst a && rhs instanceof IRIntConst b) {
                    it.val = IRBoolConst.instance(a.val < b.val);
                } else if (lhs instanceof IRStringConst a && rhs instanceof IRStringConst b) {
                    it.val = IRBoolConst.instance(a.val.compareTo(b.val) < 0);
                }
            }
            case ">" -> {
                isCmp = true;
                op = "sgt";
                if (lhs instanceof IRIntConst a && rhs instanceof IRIntConst b) {
                    it.val = IRBoolConst.instance(a.val > b.val);
                } else if (lhs instanceof IRStringConst a && rhs instanceof IRStringConst b) {
                    it.val = IRBoolConst.instance(a.val.compareTo(b.val) > 0);
                }
            }
            case "<=" -> {
                isCmp = true;
                op = "sle";
                if (lhs instanceof IRIntConst a && rhs instanceof IRIntConst b) {
                    it.val = IRBoolConst.instance(a.val <= b.val);
                } else if (lhs instanceof IRStringConst a && rhs instanceof IRStringConst b) {
                    it.val = IRBoolConst.instance(a.val.compareTo(b.val) <= 0);
                }
            }
            case ">=" -> {
                isCmp = true;
                op = "sge";
                if (lhs instanceof IRIntConst a && rhs instanceof IRIntConst b) {
                    it.val = IRBoolConst.instance(a.val >= b.val);
                } else if (lhs instanceof IRStringConst a && rhs instanceof IRStringConst b) {
                    it.val = IRBoolConst.instance(a.val.compareTo(b.val) >= 0);
                }
            }
            case "==" -> {
                isCmp = true;
                op = "eq";
                if (lhs instanceof IRIntConst a && rhs instanceof IRIntConst b) {
                    it.val = IRBoolConst.instance(a.val == b.val);
                } else if (lhs instanceof IRStringConst a && rhs instanceof IRStringConst b) {
                    it.val = IRBoolConst.instance(a.val.equals(b.val));
                }
            }
            case "!=" -> {
                isCmp = true;
                op = "ne";
                if (lhs instanceof IRIntConst a && rhs instanceof IRIntConst b) {
                    it.val = IRBoolConst.instance(a.val != b.val);
                } else if (lhs instanceof IRStringConst a && rhs instanceof IRStringConst b) {
                    it.val = IRBoolConst.instance(!a.val.equals(b.val));
                }
            }
        }
        if (it.val != null) return;
        IRRegister reg = new IRRegister(type);
        it.val = reg;
//        isCmp = "<=>=!==".contains(it.op);
        if (isCmp) {
            if (it.left.type.isString()) {
                currentBlock.addInst(new IRCallInst(reg, "string." + IRIcmpInst.toShortForm(op), lhs, rhs));
            } else {
                currentBlock.addInst(new IRIcmpInst(reg, op, lhs, rhs));
            }
        } else {
            if (it.left.type.isString()) {
                currentBlock.addInst(new IRCallInst(reg, "string." + op, lhs, rhs));
            } else currentBlock.addInst(new IRBinaryInst(reg, op, lhs, rhs));
        }
    }

    @Override
    public void visit(UnaryExprNode it) {
        it.obj.accept(this);
        toRValue(it.obj);
        IREntity obj = it.obj.val;
        IRRegister reg = null;
        if (!(obj instanceof IRConst)) {
            reg = new IRRegister(obj.type);
            it.val = reg;
        }
        switch (it.op) {
            case "~" -> {
                if (obj instanceof IRIntConst a) {
                    it.val = new IRIntConst(~a.val);
                } else {
                    currentBlock.addInst(new IRBinaryInst(reg, "xor", irIntNegative1, obj));
                }
            }
            case "-" -> {
                if (obj instanceof IRIntConst a) {
                    it.val = new IRIntConst(-a.val);
                } else {
                    currentBlock.addInst(new IRBinaryInst(reg, "sub", irInt0, obj));
                }
            }
            case "!" -> {
                if (obj instanceof IRBoolConst a) {
                    it.val = IRBoolConst.instance(!a.val);
                } else {
                    currentBlock.addInst(new IRBinaryInst(reg, "xor", irBoolTrue, obj));
                }
            }
        }
    }

    @Override
    public void visit(TernaryExprNode it) {
        IRType type = toIRType(it.type);

        it.condition.accept(this);
        toRValue(it.condition);
        IRBasicBlock
                trueBlock = currentFunc.addBlock("ternary.true"),
                falseBlock = currentFunc.addBlock("ternary.false"),
                endBlock = currentFunc.addBlock("ternary.end");
        currentBlock.addInst(new IRBranchInst(it.condition.val, trueBlock, falseBlock));

        currentBlock = trueBlock;
        it.left.accept(this);
        toRValue(it.left, type);
        trueBlock = currentBlock;
        currentBlock.setJump(endBlock);

        currentBlock = falseBlock;
        it.right.accept(this);
        toRValue(it.right, type);
        falseBlock = currentBlock;
        currentBlock.setJump(endBlock);

        currentBlock = endBlock;
        if (!it.type.isVoid()) {
            IRRegister reg = new IRRegister(type);
            it.val = reg;
            IRPhiInst phiInst = new IRPhiInst(reg);
            phiInst.addSource(trueBlock, it.left.val);
            phiInst.addSource(falseBlock, it.right.val);
            currentBlock.addInst(phiInst);
        } else it.val = irVoid;
    }

    @Override
    public void visit(AssignExprNode it) {
        it.left.accept(this);
        it.right.accept(this);
        toRValue(it.right, ((IRPtrType) it.left.val.type).pointToType());
        currentBlock.addInst(new IRStoreInst(it.right.val, (IRRegister) it.left.val));
    }

    private IRRegister visitUpdateExpr(ExprNode obj, String op) {
        obj.accept(this);
        IRRegister reg0 = new IRRegister(((IRPtrType) obj.val.type).pointToType()), reg1 = new IRRegister(reg0.type);
        currentBlock.addInst(new IRLoadInst(reg0, (IRRegister) obj.val));
        IRIntConst delta = op.equals("++") ? irInt1 : irIntNegative1;
        currentBlock.addInst(new IRBinaryInst(reg1, "add", reg0, delta));
        currentBlock.addInst(new IRStoreInst(reg1, (IRRegister) obj.val));
        return reg0;
    }

    @Override
    public void visit(PrefixUpdateExprNode it) {
        visitUpdateExpr(it.obj, it.op);
        it.val = it.obj.val;
    }

    @Override
    public void visit(SuffixUpdateExprNode it) {
        it.val = visitUpdateExpr(it.obj, it.op);
    }

    @Override
    public void visit(AtomExprNode it) {
        String content = it.content;
        it.val = it.isNull ? new IRNullConst() :
                it.isBool ? IRBoolConst.instance(content.equals("true")) :
                        it.isInt ? new IRIntConst(Integer.parseInt(content)) :
                                it.isString ? program.addStringConst(content) :
                                        currentScope.getEntity(content);
        // member variable (omitting this)
        if (it.isIdentifier && (it.val == null || it.val instanceof IRGlobalVar) && currentStruct != null && currentStruct.hasMember(it.content)) {
            it.val = visitMember(currentScope.getEntity("this"), it.content);
        }
    }

    private void addParam(String name, IRType irType, int paramIndex) {
        boolean isThis = name.equals("this");
        IRRegister param = new IRRegister(name + (isThis ? "" : ".param"), irType);
        IRRegister.reset(0);
        currentFunc.addParam(param);
        if (isThis) currentScope.addEntity(param);
        else if (!currentFunc.isBuiltin) {
            currentScope.addEntity(param);
            IRRegister paramAddr = new IRRegister(name, new IRPtrType(irType));
            currentScope.addEntity(paramAddr);
            currentBlock.addInst(new IRAllocaInst(paramAddr, paramIndex));
            currentBlock.addInst(new IRStoreInst(param, paramAddr));
        }
    }

    @Override
    public void visit(ParamsListNode it) {
        int inClass = currentScope.classScope != null ? 1 : 0;
        for (int i = 0; i < it.types.size(); i++) {
            IRType irType = toIRType(it.types.get(i));
            String name = it.names.get(i);
            addParam(name, irType, i + inClass);
        }
    }

    @Override
    public void visit(ArgsListNode it) {
        it.vals.forEach(arg -> arg.accept(this));
    }
}
