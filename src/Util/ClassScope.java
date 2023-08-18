package Util;

import AST.ConstructorDefNode;
import AST.FuncDefNode;
import Util.error.semanticError;

import java.util.HashMap;

public class ClassScope extends Scope {

    public String name;
    private HashMap<String, FuncDefNode> funcs = new HashMap<>();
    private ConstructorDefNode constructor = null;

    public ClassScope(String name) {
        super(null);
        this.name = name;
        classType = new Type(name);
        classScope = this;
    }

//    public void setParentScope(Scope parentScope) {
//        this.parentScope = parentScope;
//    }

    @Override
    public void defineVar(String name, Type t, position pos) {
        if (name.equals(this.name) || funcs.containsKey(name))
            throw new semanticError("Semantic Error: variable name used", pos);
        super.defineVar(name, t, pos);
    }

    public Type getMemberVarType(String name) {
        if (vars.containsKey(name)) return vars.get(name);
        else return null;
    }

    public void defineFunc(FuncDefNode func) {
        if (func.name.equals(this.name) || funcs.containsKey(func.name) || vars.containsKey(func.name))
            throw new semanticError("Semantic Error: function name used", func.pos);
        funcs.put(func.name, func);
    }

    public void defineConstructor(ConstructorDefNode constructor) {
        if (!constructor.name.equals(this.name))
            throw new semanticError("Semantic Error: constructor name mismatch", constructor.pos);
        this.constructor = constructor;
    }

    public FuncDefNode getFunc(String name) {
        return funcs.get(name);
    }

    public ConstructorDefNode getConstructor() {
        return constructor;
    }
}
