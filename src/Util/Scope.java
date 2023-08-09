package Util;

//import MIR.register;

import Util.error.semanticError;

import java.util.HashMap;

public class Scope {

    protected HashMap<String, Type> vars = new HashMap<>();
    //    public HashMap<String, register> entities = new HashMap<>();
    public Scope parentScope;

    public Scope(Scope parentScope) {
        this.parentScope = parentScope;
        if (parentScope != null) {
            inLoop = parentScope.inLoop;
//            inFunc = parentScope.inFunc;
            returnType = parentScope.returnType;
//            inClass = parentScope.inClass;
            classType = parentScope.classType;
            classScope = parentScope.classScope;
        }
    }

//    public Scope getParentScope() {
//        return parentScope;
//    }

    public boolean inLoop = false;
    public Type returnType = null,
            classType = null;
    public ClassScope classScope = null;
    // record nodes???

    public void defineVar(String name, Type t, position pos) {
        //TODO clash with global function (what about class scope?)
        if (vars.containsKey(name))
            throw new semanticError("Semantic Error: variable name used", pos);
        vars.put(name, t);
    }

    public boolean containsVar(String name) {
        if (vars.containsKey(name)) return true;
        else if (parentScope != null)
            return parentScope.containsVar(name);
        else return false;
    }

    public Type getVarType(String name) {
        if (vars.containsKey(name)) return vars.get(name);
        else if (parentScope != null)
            return parentScope.getVarType(name);
        else return null;
    }

//    public register getEntity(String name, boolean lookUpon) {
//        if (entities.containsKey(name)) return entities.get(name);
//        else if (parentScope != null && lookUpon)
//            return parentScope.getEntity(name, true);
//        return null;
//    }
}
