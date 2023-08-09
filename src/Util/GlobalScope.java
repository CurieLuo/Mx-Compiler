package Util;

import Util.error.semanticError;
import AST.*;

import java.util.HashMap;

public class GlobalScope extends Scope {
    private HashMap<String, ClassScope> classes = new HashMap<>();
    private HashMap<String, FuncDefNode> funcs = new HashMap<>();

    private Builtins builtins = new Builtins();

    public GlobalScope() {
        super(null);

        defineClass(Builtins.stringClass);
        defineClass(Builtins.intClass);
        defineClass(Builtins.boolClass);
        defineClass(Builtins.voidClass);

        defineFunc(Builtins.print);
        defineFunc(Builtins.println);
        defineFunc(Builtins.printInt);
        defineFunc(Builtins.printlnInt);
        defineFunc(Builtins.getInt);
        defineFunc(Builtins.getString);
        defineFunc(Builtins.toString);
    }

    @Override
    public void defineVar(String name, Type t, position pos) {
        if (funcs.containsKey(name))
            throw new semanticError("Semantic Error: variable name used", pos);
        super.defineVar(name, t, pos);
    }

    public void defineFunc(FuncDefNode func) {
        if (funcs.containsKey(func.name) || vars.containsKey(func.name) || classes.containsKey(func.name))
            throw new semanticError("Semantic Error: function name used", func.pos);
        funcs.put(func.name, func);
    }

    public void defineClass(ClassDefNode cls) {
        if (funcs.containsKey(cls.name) || classes.containsKey(cls.name))
            throw new semanticError("Semantic Error: class name used", cls.pos);
        cls.scope.parentScope = this;
        classes.put(cls.name, cls.scope);
    }

    public boolean containsType(Type type) {
        return containsType(type.name);
    }

    public boolean containsType(String typename) {
        return classes.containsKey(typename);
    }

    public ClassScope getClass(String name) {
        return classes.get(name);
    }

    public FuncDefNode getFunc(String name) {
        return funcs.get(name);
    }
}
