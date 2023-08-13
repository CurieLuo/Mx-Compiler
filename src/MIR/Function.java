package MIR;

import MIR.Entity.IRRegister;
import MIR.Inst.IRAllocaInst;
import MIR.Type.IRType;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;

public class Function {
    public String name;
    public IRType returnType;
    public ArrayList<IRRegister> params = new ArrayList<>();
    public LinkedList<BasicBlock> blocks = new LinkedList<>();

    public ArrayList<IRAllocaInst> allocas = new ArrayList<>();
    //all alloca instructions are relocated at the start of the function


    public Function(String name, IRType returnType) {
        this.name = name;
        this.returnType = returnType;
        addBlock(new BasicBlock(this, "entry"));
        addBlock(new BasicBlock(this, "return"));
    }

    public BasicBlock entryBlock() {
        return blocks.get(0);
    }

    public BasicBlock returnBlock() {
        return blocks.get(1);
    }

    public void addParam(IRRegister param) {
        params.add(param);
    }

    public BasicBlock addBlock(BasicBlock block) {
        blocks.add(block);
        return block;
    }

    public void finish() {
        entryBlock().insts.addAll(0, allocas);
    }

    @Override
    public String toString() {
        String ret = "define dso_local %s @%s(".formatted(returnType, name);
        for (int i = 0, n = params.size(); i < n; i++) {
            if (i != 0) ret += ", ";
            ret += params.get(i).toTypedFormat();
        }
        ret += ") {\n";
        for (var block : blocks) ret += block;
        ret += "}\n";
        return ret;
    }
}
