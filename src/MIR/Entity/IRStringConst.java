package MIR.Entity;

import Util.Builtins;

public class IRStringConst extends IRConst {
    public String val;
    public int id;
    private static int cnt = 0;

    public IRStringConst(String val) {
        super(Builtins.irStringType);
        this.val = val;
        id = cnt++;
    }

    @Override
    public String toString() {
        return "@.str." + id;
    }


    public String escaped() {
        return val.replace("\\", "\\\\")
                .replace("\n", "\\0A")
                .replace("\"", "\\22") + "\\00";
    }//escape
}
