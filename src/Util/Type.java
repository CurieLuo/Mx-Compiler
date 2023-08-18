package Util;

import java.util.HashMap;

public class Type {
    //    public boolean isInt = false, isBool = false, isString = false;
    public String name;
    public int dim = 0;
    public HashMap<String, Type> members = new HashMap<>();

    public Type(String name) {
        this.name = name;
    }

    public Type(String name, int dim) {
        this.name = name;
        this.dim = dim;
    }

    public boolean isBool() {
        return dim == 0 && name.equals("bool");
    }

    public boolean isInt() {
        return dim == 0 && name.equals("int");
    }

    public boolean isString() {
        return dim == 0 && name.equals("string");
    }

    public boolean isNull() {
        return name.equals("null");
    }

    public boolean isArray() {
        return dim > 0;
    }

    public boolean isVoid() {
        return name.equals("void");
    }

    public boolean isBasicType() {
        return isBool() || isInt() || isString();
    }

    @Override
    public boolean equals(Object obj) {
        Type rhs = (Type) obj;
        return (name.equals(rhs.name) && dim == rhs.dim)
                || (isNull() && !rhs.isBasicType())
                || (rhs.isNull() && !isBasicType());
    }

    public Type copy() {
        return new Type(name, dim);
    }
}
