package Assembly.Operand;

public abstract class GlobalSymbol extends Reg {
    public String name;

    public GlobalSymbol(String name) {
        this.name = name;
    }
}
