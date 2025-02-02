package AST;

import Util.position;

public class AtomExprNode extends ExprNode {
    public String content;
    public boolean isBool, isInt, isString, isNull, isThis, isIdentifier;

    public AtomExprNode(position pos, String content) {
        super(pos);
        this.content = content;
        switch (content) {
            case "true", "false" -> isBool = true;
            case "null" -> isNull = true;
            case "this" -> isThis = true;
            default -> {
                char beg = content.charAt(0);
                if (beg == '"') {
                    isString = true;
                    this.content = content.substring(1, content.length() - 1).replace("\\\\", "\\")
                            .replace("\\n", "\n")
                            .replace("\\\"", "\""); // unescape
                } else if ('0' <= beg && beg <= '9') isInt = true;
                else isIdentifier = true;
            }
        }
        assignable = isIdentifier;
    }

    @Override
    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }
}
