import AST.RootNode;

import Backend.IRBuilder;
import Parser.MxLexer;
import Parser.MxParser;
import Frontend.ASTBuilder;
import Frontend.SymbolCollector;
import Frontend.SemanticChecker;
import Util.Builtins;
import Util.GlobalScope;
import Util.MxErrorListener;
import Util.error.error;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;

import java.io.FileInputStream;
import java.io.InputStream;

//debug
import MIR.BasicBlock;
import MIR.Entity.IRRegister;
import MIR.Function;
import MIR.Inst.IRAllocaInst;
import MIR.Type.IRPtrType;
import MIR.Program;

public class Compiler {
    public static void main(String[] args) throws Exception {
        boolean local = false;
        for (var arg : args) {
            if (arg.equals("--local")) {
                local = true;
            }
        }

        InputStream input = System.in;
        if (local) input = new FileInputStream("test.mx");
        try {
            RootNode ASTRoot;
            GlobalScope gScope = new GlobalScope();

            MxLexer lexer = new MxLexer(CharStreams.fromStream(input));
            lexer.removeErrorListeners();
            lexer.addErrorListener(new MxErrorListener());
            MxParser parser = new MxParser(new CommonTokenStream(lexer));
            parser.removeErrorListeners();
            parser.addErrorListener(new MxErrorListener());
            ParseTree parseTreeRoot = parser.program();
            ASTBuilder astBuilder = new ASTBuilder();
            ASTRoot = (RootNode) astBuilder.visit(parseTreeRoot);
            new SymbolCollector(gScope).visit(ASTRoot);
            new SemanticChecker(gScope).visit(ASTRoot);
//            new IRBuilder(gScope).visit(ASTRoot);
            if (local) {
                var prgrm = new Program();
                System.out.println(prgrm);
//                var reg = new IRRegister("reg1", new IRPtrType(Builtins.irIntType));
//                var reg2 = new IRRegister("reg1", new IRPtrType(Builtins.irBoolType));
//                var alloca = new IRAllocaInst(null, reg);
//                var alloca2 = new IRAllocaInst(null, reg2);
//                var fn = new Function("fun", Builtins.irBoolType);
//                var blk = new BasicBlock(fn, "blk1");
//                fn.addBlock(blk);
//                blk.addInst(alloca);
//                blk.addInst(alloca2);
//                fn.finish();
//                System.out.println(fn);
            }
        } catch (error er) {
            System.err.println(er);
            throw new RuntimeException();
        }
    }
}