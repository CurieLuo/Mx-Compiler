import AST.RootNode;

import Frontend.IRBuilder;
import MIR.Program;
import Parser.MxLexer;
import Parser.MxParser;
import Frontend.ASTBuilder;
import Frontend.SymbolCollector;
import Frontend.SemanticChecker;
import Util.GlobalScope;
import Util.MxErrorListener;
import Util.error.error;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;

import java.io.FileInputStream;
import java.io.InputStream;

//debug


public class Compiler {
    public static void main(String[] args) throws Exception {
        boolean local = false, syntaxOnly = false;
        for (var arg : args) {
            if (arg.equals("--local")) {
                local = true;
            } else if (arg.equals("-fsyntax-only")) {
                syntaxOnly = true;
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
            if (syntaxOnly) return; // semantic part

            Program program = new Program();
            new IRBuilder(gScope, program).visit(ASTRoot);
            System.out.println(program); // IR part debug
        } catch (error er) {
            System.err.println(er);
            throw new RuntimeException();
        }
    }
}