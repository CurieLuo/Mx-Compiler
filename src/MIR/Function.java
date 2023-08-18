package MIR;

import MIR.Entity.IRRegister;
import MIR.Inst.IRAllocaInst;
import MIR.Type.IRType;

import java.util.ArrayList;
import java.util.LinkedList;

public class Function {
    public String name;
    public IRType returnType;
    public boolean isBuiltin;
    public ArrayList<IRRegister> params = new ArrayList<>();
    public LinkedList<BasicBlock> blocks = new LinkedList<>();

    public ArrayList<IRAllocaInst> allocas = new ArrayList<>();
    //all alloca instructions are relocated at the start of the function

    public Function(String name, IRType returnType, boolean isBuiltin, IRRegister... params) {
        this.name = name;
        this.returnType = returnType;
        this.isBuiltin = isBuiltin;
        if (!isBuiltin) {
            addBlock(new BasicBlock("entry"));
            addBlock(new BasicBlock("return"));
        }
        for (var param : params) addParam(param);
    }

    public BasicBlock entryBlock() {
        if (isBuiltin) return null;
        return blocks.get(0);
    }

    public BasicBlock returnBlock() {
        if (isBuiltin) return null;
        return blocks.get(1);
    }

    public void addParam(IRRegister param) {
        params.add(param);
    }

    public BasicBlock addBlock(BasicBlock block) {
        blocks.add(block);
        block.parentFunc = this;
        return block;
    }

    public BasicBlock addBlock(String label) {
        return addBlock(new BasicBlock(label));
    }

    public void finish() {
        entryBlock().insts.addAll(0, allocas);
    }

    @Override
    public String toString() {
        String ret = "%s dso_local %s @%s(".formatted(isBuiltin ? "declare" : "define", returnType, name);
        for (int i = 0, n = params.size(); i < n; i++) {
            if (i != 0) ret += ", ";
            if (isBuiltin) ret += params.get(i).type;
            else ret += params.get(i).toTypedFormat();
        }
        ret += ")";
        if (!isBuiltin) {
            ret += "{\n";
            for (var block : blocks) ret += block;
            ret += "}\n";
        }
        return ret;
    }
}
