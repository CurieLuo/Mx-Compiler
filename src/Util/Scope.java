package Util;

import AST.LoopStmtNode;
import IR.Entity.IRRegister;
import Util.error.semanticError;

import java.util.HashMap;

public class Scope {

    protected HashMap<String, Type> vars = new HashMap<>();
    public Scope parentScope;

    public Scope(Scope parentScope) {
        this.parentScope = parentScope;
        if (parentScope != null) {
            loop = parentScope.loop;
            returnType = parentScope.returnType;
            classType = parentScope.classType;
            classScope = parentScope.classScope;
        }
    }

    public LoopStmtNode loop = null;
    public Type returnType = null,
            classType = null;
    public ClassScope classScope = null;

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

    public HashMap<String, IRRegister> entities = new HashMap<>();

    public void addEntity(IRRegister entity) {
        entities.put(entity.name, entity);
    }

    public IRRegister getEntity(String name) {
        if (entities.containsKey(name)) return entities.get(name);
        else if (parentScope != null)
            return parentScope.getEntity(name);
        else return null;
    }

    public void removeVars() {
        vars.clear();
    }
}
