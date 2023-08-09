import AST.RootNode;

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

public class Main {
    public static void main(String[] args) throws Exception {

//        String name = "test.mx";
//        InputStream input = new FileInputStream(name);
        InputStream input = System.in;
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

        } catch (error er) {
            System.err.println(er);
            throw new RuntimeException();
        }
    }
}