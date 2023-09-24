package Backend;

import Assembly.*;
import Assembly.Inst.*;
import Assembly.Operand.*;
import IR.*;
import IR.Entity.*;
import IR.Inst.*;

import static Assembly.Operand.PhyReg.*;

import java.util.HashMap;

public class AsmBuilder implements IRVisitor {
    private AsmModule module;
    private AsmFunction currentFunc = null;
    private AsmBlock currentBlock = null;

    private HashMap<IRBasicBlock, AsmBlock> blocksMap = new HashMap<>();

    private static Imm imm1 = new Imm(1), imm2 = new Imm(2);

    private static HashMap<Integer, Integer> log2Map = new HashMap<>();

    static {
        for (int i = 0; i < 31; i++)
            log2Map.put(1 << i, i);
    }

    public AsmBuilder(AsmModule module) {
        this.module = module;
    }

    public Reg immToReg(int val, Reg reg) {
        if (val == 0) return zero;
        currentBlock.addInst(new AsmLiInst(reg, new Imm(val)));
        return reg;
    }

    public Reg immToReg(int val) {
        if (val == 0) return zero;
        return immToReg(val, new VirtualReg());
    }

    public Reg getReg(Entity entity) {
        if (entity.reg == null) {
            if (entity instanceof IRConst irConst) {
                int val = irConst.toInt();
                return immToReg(val);
            }
            entity.reg = new VirtualReg(entity.type.size);
        } else if (entity.reg instanceof GlobalSymbol globalSymbol) {
            VirtualReg ptr = new VirtualReg();
            currentBlock.addInst(new AsmLaInst(ptr, globalSymbol.name));
            return ptr;
        }
        return entity.reg;
    }

    private void load(int size, Reg rd, Reg rs1, int offset) {
        // offset >= 0 by default
        if (offset < 1 << 11) {
            currentBlock.addInst(new AsmLoadInst(size, rd, rs1, new Imm(offset)));
        } else {
//            VirtualReg tmp = new VirtualReg();
            currentBlock.addInst(new AsmRTypeInst("add", rd, rs1, immToReg(offset, rd)));
            currentBlock.addInst(new AsmLoadInst(size, rd, rd));
        }
    }

    private void store(int size, Reg rs2, Reg rs1, int offset) {
        if (offset < 1 << 11) {
            currentBlock.addInst(new AsmStoreInst(size, rs2, rs1, new Imm(offset)));
        } else {
            VirtualReg tmp = new VirtualReg();
            currentBlock.addInst(new AsmRTypeInst("add", tmp, rs1, immToReg(offset, tmp)));
            currentBlock.addInst(new AsmStoreInst(size, rs2, tmp));
        }
    }

    private void link(AsmBlock pred, AsmBlock succ) {
        pred.succ.add(succ);
        succ.pred.add(pred);
    }

    @Override
    public void visit(IRProgram it) {
        for (var var : it.globalVars) {
            var.reg = module.addValue(new GlobalValue(var));
        }
        for (var str : it.stringConsts.values()) {
            str.reg = module.addString(new GlobalStringConst(str.toString().substring(1), str.val));
        }
        for (var func : it.funcs)
            if (!func.isBuiltin) {
                currentFunc = module.addFunc(new AsmFunction(func.name));
                func.accept(this);
            }
    }

    @Override
    public void visit(IRFunction it) {
        VirtualReg.reset();
        int maxArgCount = 0;
        for (int i = 0; i < it.blocks.size(); i++) {
            var irBlock = it.blocks.get(i);
            AsmBlock asmBlock = i != 0 ? new AsmBlock() : new AsmBlock(currentFunc.name);
            blocksMap.put(irBlock, asmBlock);
            currentFunc.blocks.add(asmBlock);
            for (var inst : irBlock.insts)
                if (inst instanceof IRCallInst call) maxArgCount = Math.max(maxArgCount, call.args.size());
        }
        currentFunc.paramSpaceSize = maxArgCount > 8 ? (maxArgCount - 8) * 4 : 0;
        for (var param : it.params) param.reg = new VirtualReg(param.type.size);
        for (int i = 0; i < it.blocks.size(); i++) {
            var block = it.blocks.get(i);
            currentBlock = currentFunc.blocks.get(i);
            if (i == 0) {
                store(4, ra, sp, currentFunc.paramSpaceSize);
                // get arguments
                for (int j = 0; j < Math.min(it.params.size(), 8); j++) {
                    currentBlock.addInst(new AsmMvInst(it.params.get(j).reg, PhyReg.get("a" + j)));
                }
            }
            block.accept(this);
        }

        // preserve callee-saved registers
        //TODO!!!!!!!
//        for (var reg : calleeSaved) {
//            VirtualReg tmp = new VirtualReg();
//            currentFunc.addFirstInst(new AsmMvInst(tmp, reg));
//            currentFunc.addLastInst(new AsmMvInst(reg, tmp));
//        }

        currentFunc.blocks.forEach(block -> block.finish());
        currentFunc.virtualRegCount = VirtualReg.count;
    }

    @Override
    public void visit(IRBasicBlock it) {
        // TODO combine icmp & br
        for (var inst : it.insts) {
            inst.accept(this);
        }
        it.terminatorInst.accept(this);
    }

    @Override
    public void visit(IRAllocaInst it) {
        Reg ptr = getReg(it.reg);
        if (it.paramIndex < 8) {
            // including non-params (paramIndex = -1)
            int offset = currentFunc.stackSize();
            if (offset < 1 << 11) {
                currentBlock.addInst(new AsmITypeInst("addi", ptr, sp, new Imm(offset)));
            } else {
                currentBlock.addInst(new AsmRTypeInst("add", ptr, sp, immToReg(offset)));
            }
            currentFunc.allocSize += 4;
        } else {
            int offset = (it.paramIndex - 8) * 4;
//            Reg tmp = new VirtualReg();
            currentBlock.addInst(new AsmLiInst(ptr, new ParamAddrOffset(currentFunc, offset)));
            // ParamAddrOffset: to be determined after visiting function
            currentBlock.addInst(new AsmRTypeInst("add", ptr, ptr, sp));
        }
    }

    @Override
    public void visit(IRBinaryInst it) {
        switch (it.op) {
            case "mul", "add", "and", "xor", "or" -> {
                if (it.left instanceof IRConst) {
                    Entity tmp = it.left;
                    it.left = it.right;
                    it.right = tmp;
                }
            }
        }

        Reg reg = getReg(it.reg), lhs = getReg(it.left);

        if (it.right instanceof IRIntConst intConst) {
            if (it.op.equals("mul") && log2Map.containsKey(intConst.val)) {
                it.op = "shl";
                intConst.val = log2Map.get(intConst.val);
            }
            switch (it.op) {
                case "add", "sub", "shl", "ashr", "and", "xor", "or" -> {
                    if (Math.abs(intConst.val) < 1 << 11) {
                        if (it.op.equals("sub")) {
                            it.op = "add";
                            intConst.val = -intConst.val;
                        }
                        currentBlock.addInst(new AsmITypeInst(it.op + "i", reg, lhs, new Imm(intConst.val)));
                        return;
                    }
                }
            }
        }

        currentBlock.addInst(new AsmRTypeInst(it.op, reg, lhs, getReg(it.right)));
    }

    @Override
    public void visit(IRBranchInst it) {
        AsmBlock trueBlock = blocksMap.get(it.trueBlock), falseBlock = blocksMap.get(it.falseBlock);
        currentBlock.addInst(new AsmBeqzInst(getReg(it.condition), falseBlock));
        currentBlock.addInst(new AsmJumpInst(trueBlock));
        link(currentBlock, trueBlock);
        link(currentBlock, falseBlock);
    }

    @Override
    public void visit(IRCallInst it) {
        for (int i = 0; i < it.args.size(); i++) {
            var arg = it.args.get(i);
            var argVal = getReg(arg);
            if (i < 8)
                currentBlock.addInst(new AsmMvInst(PhyReg.get("a" + i), argVal));
            else store(arg.type.size, argVal, sp, (i - 8) * 4);
        }
        currentBlock.addInst(new AsmCallInst(it.name));
        if (it.reg != null) currentBlock.addInst(new AsmMvInst(getReg(it.reg), PhyReg.a0));
    }

    @Override
    public void visit(IRGetElementPtrInst it) {
        Reg index = getReg(it.indices.getLast());
        if (it.pointToType.size == 1) {
            currentBlock.addInst(new AsmRTypeInst("add", getReg(it.reg), getReg(it.pointer), index));
            return;
        }
//        Reg tmp = new VirtualReg();
        Reg reg = getReg(it.reg);
        currentBlock.addInst(new AsmITypeInst("slli", reg, index, imm2));
        currentBlock.addInst(new AsmRTypeInst("add", reg, reg, getReg(it.pointer)));
    }

    @Override
    public void visit(IRIcmpInst it) {
        Reg reg = getReg(it.reg), lhs = getReg(it.left), rhs = getReg(it.right);
        String op = IRIcmpInst.toShortForm(it.op);
        switch (op) {
            case "eq", "ne" -> {
                VirtualReg tmp = new VirtualReg();
                currentBlock.addInst(new AsmRTypeInst("xor", tmp, lhs, rhs));
                currentBlock.addInst(new AsmSetzInst(op, reg, tmp));
            }
            case "lt", "ge" -> {
                currentBlock.addInst(new AsmRTypeInst("slt", reg, lhs, rhs));
                if (op.equals("ge")) currentBlock.addInst(new AsmITypeInst("xori", reg, reg, imm1));
            }
            case "gt", "le" -> {
                currentBlock.addInst(new AsmRTypeInst("slt", reg, rhs, lhs));
                if (op.equals("le")) currentBlock.addInst(new AsmITypeInst("xori", reg, reg, imm1));
            }
        }
    }

    @Override
    public void visit(IRJumpInst it) {
        AsmBlock destBlock = blocksMap.get(it.destBlock);
        currentBlock.addInst(new AsmJumpInst(destBlock));
        link(currentBlock, destBlock);
    }

    @Override
    public void visit(IRLoadInst it) {
        Reg reg = ((IRRegister) it.pointer).name.equals("ret.val") ? a0 : getReg(it.reg);
        Reg ptr = getReg(it.pointer);
        load(it.reg.type.size, reg, ptr, 0);
    }

    @Override
    public void visit(IRPhiInst it) {
        Reg tmp = new VirtualReg(it.reg.type.size);
        for (int i = 0; i < it.vals.size(); i++) {
            Entity val = it.vals.get(i);
            AsmBlock src = blocksMap.get(it.sourceBlocks.get(i));
            if (val instanceof IRConst constVal)
                src.phiSrcInsts.add(new AsmLiInst(tmp, new Imm(constVal.toInt())));
            else
                src.phiSrcInsts.add(new AsmMvInst(tmp, getReg(val)));
        }
        currentBlock.addInst(new AsmMvInst(getReg(it.reg), tmp));
    }

    @Override
    public void visit(IRRetInst it) {
        load(4, ra, sp, currentFunc.paramSpaceSize);
        // add ret instruction after stack allocation
    }

    @Override
    public void visit(IRStoreInst it) {
        Reg val = getReg(it.val);
        Reg ptr = getReg(it.pointer);
        store(it.val.type.size, val, ptr, 0);
    }
}
