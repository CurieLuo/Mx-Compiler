import AST.RootNode;
import Assembly.AsmModule;
import IR.IRFunction;
import IR.IRProgram;
import Parser.MxLexer;
import Parser.MxParser;
import Frontend.*;
import Optimize.*;
import Backend.*;
import Util.GlobalScope;
import Util.MxErrorListener;
import Util.error.error;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

public class Compiler {
    public static void main(String[] args) throws Exception {
        boolean localFlag = false, syntaxOnlyFlag = false, irFlag = false, asmFlag = false;
        for (var arg : args)
            switch (arg) {
                case "-local" -> {
                    localFlag = true;
                }
                case "-fsyntax-only" -> {
                    syntaxOnlyFlag = true;
                }
                case "-ir" -> {
                    irFlag = true;
                }
                case "-S" -> {
                    asmFlag = true;
                }
            }

        InputStream input = System.in;
        if (localFlag) input = new FileInputStream("test.mx");
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
            if (syntaxOnlyFlag) return; // semantic part

            IRProgram program = new IRProgram();
            new IRBuilder(gScope, program).visit(ASTRoot);
//            new IROptimizer(program).work();
//            program.funcs.forEach(IRFunction::finish);
            OutputStream irOutput = irFlag ? System.out : new FileOutputStream("test.ll");
            irOutput.write(program.toString().getBytes()); // IR part debug

            AsmModule module = new AsmModule();
            new AsmBuilder(module).visit(program);
            new NaiveRegAllocator(module).work();
            new StackAllocator(module).work();
            OutputStream asmOutput = asmFlag ? System.out : new FileOutputStream("test.s");
            asmOutput.write(module.toString().getBytes()); // assembly part
        } catch (error er) {
            System.err.println(er);
            throw new RuntimeException();
        }
    }
}