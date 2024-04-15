package DataClass;

import java.util.HashMap;

public abstract class CompoundDataClass implements DataClass {
    HashMap<String, DataClass> inners = new HashMap<>();
    public abstract boolean minimumValuesAreSet(CompoundDataClassBrick cdcb, DCHolder dcHolder);
    public DataClass getInner(String innerName) {
        return inners.get(innerName);
    }
    public abstract DataClassBrick derive(CompoundDataClassBrick cdcb, String innerName, DCHolder dcHolder); //where dcb = the dcb being derived
    public abstract CompoundDataClassBrick makeBrick(DCHolder dcHolder);
    public void putInner(String innerName, DataClass innerVal) {
        inners.put(innerName, innerVal);
    }
}
