package Util;

import AST.*;
import MIR.Entity.*;
import MIR.Type.*;

public class Builtins {
    // only for assigning type to expression
    public static Type voidType = new Type("void"),
            nullType = new Type("null"),
            intType = new Type("int"),
            boolType = new Type("bool"),
            stringType = new Type("string");

    public static ClassDefNode voidClass = new ClassDefNode(null, "void"),
            intClass = new ClassDefNode(null, "int"),
            boolClass = new ClassDefNode(null, "bool"),
            stringClass = new ClassDefNode(null, "string");
    public static ClassScope arrayClassScope = new ClassScope("");

    public static FuncDefNode print = new FuncDefNode(null, "print"),
            println = new FuncDefNode(null, "println"),
            printInt = new FuncDefNode(null, "printInt"),
            printlnInt = new FuncDefNode(null, "printlnInt"),
            getString = new FuncDefNode(null, "getString"),
            getInt = new FuncDefNode(null, "getInt"),
            toString = new FuncDefNode(null, "toString");

    /*public*/ static FuncDefNode length = new FuncDefNode(null, "length"),
            substring = new FuncDefNode(null, "substring"),
            parseInt = new FuncDefNode(null, "parseInt"),
            ord = new FuncDefNode(null, "ord"),
            size = new FuncDefNode(null, "size");

    public static IRType irVoidType = new IRVoidType(),
            irCharType = new IRIntType(8),
            irBoolType = irCharType,
            irCondType = new IRIntType(1),
            irIntType = new IRIntType(32),
            irNullType = new IRPtrType(irVoidType),
            irStringType = new IRPtrType(irCharType); //TODO use C-string!!!

    public static IRConst irNull = new IRNullConst(),
            irCondTrue = new IRCondConst(true),
            irCondFalse = new IRCondConst(false),
            irBoolTrue = new IRBoolConst(true),
            irBoolFalse = new IRBoolConst(false),
            irInt0 = new IRIntConst(0),
            irInt1 = new IRIntConst(1);

    public Builtins() {
        ParamsListNode paramString = new ParamsListNode(null);
        paramString.types.add(stringType);
        paramString.names.add("str");
        ParamsListNode paramInt = new ParamsListNode(null);
        paramInt.types.add(intType);
        paramInt.names.add("n");
        ParamsListNode paramsIntInt = new ParamsListNode(null);
        paramsIntInt.types.add(intType);
        paramsIntInt.names.add("left");
        paramsIntInt.types.add(intType);
        paramsIntInt.names.add("right");
        //TODO different param names for the same type

        print.params = println.params = paramString;
        printInt.params = printlnInt.params = toString.params = paramInt;
        print.returnType = println.returnType = printInt.returnType = printlnInt.returnType = voidType;
        getString.returnType = toString.returnType = stringType;
        getInt.returnType = intType;

        substring.params = paramsIntInt;
        ord.params = paramInt;
        length.returnType = parseInt.returnType = ord.returnType = intType;
        substring.returnType = stringType;
        stringClass.defs.add(length);
        stringClass.defs.add(substring);
        stringClass.defs.add(parseInt);
        stringClass.defs.add(ord);
        stringClass.scope.defineFunc(length);
        stringClass.scope.defineFunc(substring);
        stringClass.scope.defineFunc(parseInt);
        stringClass.scope.defineFunc(ord);

        size.returnType = intType;
        arrayClassScope.defineFunc(size);
    }

}
