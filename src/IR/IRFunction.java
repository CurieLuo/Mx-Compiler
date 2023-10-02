package IR;

import IR.Entity.IRRegister;
import IR.Inst.IRAllocaInst;
import IR.Type.IRType;

import java.util.ArrayList;
import java.util.LinkedList;

public class IRFunction {
    public String name;
    public IRType returnType;
    public boolean isBuiltin;
    public ArrayList<IRRegister> params = new ArrayList<>();
    public LinkedList<IRBasicBlock> blocks = new LinkedList<>();

    public ArrayList<IRAllocaInst> allocaInsts = new ArrayList<>();
    //all alloca instructions are relocated at the start of the function

    public IRFunction(String name, IRType returnType, boolean isBuiltin, IRRegister... params) {
        this.name = name;
        this.returnType = returnType;
        this.isBuiltin = isBuiltin;
        if (!isBuiltin) {
            addBlock(new IRBasicBlock("entry"));
            addBlock(new IRBasicBlock("return"));
        }
        for (var param : params) addParam(param);
    }

    public IRBasicBlock entryBlock() {
        if (isBuiltin) return null;
        return blocks.get(0);
    }

    public IRBasicBlock returnBlock() {
        if (isBuiltin) return null;
        return blocks.get(1);
    }

    public void addParam(IRRegister param) {
        params.add(param);
    }

    public IRBasicBlock addBlock(IRBasicBlock block) {
        blocks.add(block);
        block.parentFunc = this;
        return block;
    }

    public IRBasicBlock addBlock(String label) {
        return addBlock(new IRBasicBlock(label));
    }

    public void finish() {
        if (isBuiltin) return;
        entryBlock().insts.addAll(0, allocaInsts); // TODO not needed if mem2reg is switched on
    }

    public void accept(IRVisitor visitor) {
        visitor.visit(this);
    }

    @Override
    public String toString() {
        StringBuilder ret = new StringBuilder("%s dso_local %s @%s(".formatted(isBuiltin ? "declare" : "define", returnType, name));
        for (int i = 0; i < params.size(); i++) {
            if (i != 0) ret.append(", ");
            if (isBuiltin) ret.append(params.get(i).type);
            else ret.append(params.get(i).toTypedFormat());
        }
        ret.append(")");
        if (!isBuiltin) {
            ret.append("{\n");
            for (var block : blocks) ret.append(block);
            ret.append("}\n");
        }
        return ret.toString();
    }
}
