package IR.Type;

import java.util.ArrayList;
import java.util.HashMap;

public class IRStructType extends IRType {
    public ArrayList<IRType> memberTypes = new ArrayList<>();
    public HashMap<String, Integer> memberIndices = new HashMap<>();

    public IRStructType(String name) {
        super("%struct." + name, 0);
    }

    public void addMember(String name, IRType type) {
        memberIndices.put(name, memberTypes.size());
        memberTypes.add(type);
        size += 4; // 4B-aligned (C++: align only when int/ptr member exists)
    }

    public boolean hasMember(String name) {
        return memberIndices.containsKey(name);
    }

    public int getIndex(String name) {
        return memberIndices.get(name);
    }

    public String toDefFormat() {
        String ret = "%s = type { ".formatted(this);
        for (int i = 0; i < memberTypes.size(); i++) {
            if (i != 0) ret += ", ";
            ret += memberTypes.get(i);
        }
        ret += " }\n";
        return ret;
    }
}