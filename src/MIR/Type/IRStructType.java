package MIR.Type;

import java.util.ArrayList;
import java.util.HashMap;

public class IRStructType extends IRType {
    public ArrayList<IRType> memberTypes = new ArrayList<>();
    public HashMap<String, Integer> memberIndices = new HashMap<>();

    public IRStructType(String name) {
        super("@struct." + name, 0);
    }

    public void addMember(String name, IRType type) {
        memberIndices.put(name, memberTypes.size());
        memberTypes.add(type);
        size += 4; //TODO 4B align?(C++: align only when int/ptr member exists) empty struct??(C++: size=1B)
    }

    public boolean hasMember(String name) {
        return memberIndices.containsKey(name);
    }
}