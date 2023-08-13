package MIR.Type;

public class IRArrayType extends IRType {
    public IRType valType;
    public int length;

    public IRArrayType(IRType valType, int length) {
        super("[%d x %s]".formatted(length, valType.name), valType.size * length);
        this.valType = valType;
        this.length = length;
    }
}