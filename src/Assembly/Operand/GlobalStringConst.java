package Assembly.Operand;

public class GlobalStringConst extends GlobalSymbol {
    String str;

    public GlobalStringConst(String name, String str) {
        super(name);
        this.str = str;
    }

    @Override
    public String toString() {
        String escaped = str.replace("\\", "\\\\")
                .replace("\n", "\\n")
                .replace("\"", "\\\"");
        return "%s:\n  .asciz \"%s\"\n".formatted(name, escaped);
    }
}
