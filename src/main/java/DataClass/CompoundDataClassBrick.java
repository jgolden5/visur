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
  public void putInner(String innerName, DataClassBrick innerVal) {
    inners.put(innerName, innerVal);
  }
  public DataClassBrick calculateInner(String name, DCHolder dcHolder) {
    DataClassBrick inner = getInner(name);
    CompoundDataClass outerDC = (CompoundDataClass) getDC();
    DataClassBrick res = null;
    if(outerDC.minimumValuesAreSet(this, dcHolder)) {
        if (inner instanceof PrimitiveDataClassBrick &&
                ((PrimitiveDataClassBrick) inner).getVal() == null) {
            res = outerDC.derive(this, name, dcHolder);
        } else {
            res = inner;
        }
    }
    return res;
  }

}
