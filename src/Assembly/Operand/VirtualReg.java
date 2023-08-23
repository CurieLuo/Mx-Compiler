package Assembly.Operand;

public class VirtualReg extends Reg {
    public int size = 4, id = -1;

    public static int count = 0;

    public VirtualReg() {
        this.id = count++;
    }

    public VirtualReg(int size) {
        this();
        this.size = size;
    }

    public static void reset() {
        count = 0;
    }

    @Override
    public String toString() {
        return "%" + id;
    }
}
