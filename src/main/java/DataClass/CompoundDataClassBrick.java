package DataClass;

import java.util.HashMap;

public class CompoundDataClassBrick extends DataClassBrick {
    CompoundDataClass dc;
    HashMap<String, DataClassBrick> inners;
    private CompoundDataClassBrick(DataClass dc, CompoundDataClassBrick outer, HashMap<String, DataClassBrick> inners) {
        super(dc, outer);
        this.inners = inners;
    }
    public static CompoundDataClassBrick make(DataClass dc, CompoundDataClassBrick outer, HashMap<String, DataClassBrick> inners) {
        return new CompoundDataClassBrick(dc, outer, inners);
    }
    public DataClassBrick getInner(String name) {
        return inners.get(name);
    }
    public DataClassBrick calculateInner(String name, DCHolder dcHolder) {
        DataClassBrick inner = getInner(name);
        CompoundDataClassBrick outerDCB = inner.getOuter();
        CompoundDataClass outerDC = (CompoundDataClass) outerDCB.getDC();
        DataClassBrick res = null;
        if(outerDC.minimumValuesAreSet(outerDCB, dcHolder)) {
            if (inner instanceof PrimitiveDataClassBrick &&
                    ((PrimitiveDataClassBrick) inner).getVal() == null) {
                res = outerDC.derive(outerDCB, name, dcHolder);
            } else {
                res = inner;
            }
        }
        return res;
    }

    public void putInner(String innerName, DataClassBrick innerVal) {
        inners.put(innerName, innerVal);
    }
}
