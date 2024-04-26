package DataClass;

import CursorPositionDC.CursorPositionDCHolder;

import java.util.HashMap;

public class CompoundDataClassBrick extends DataClassBrick {
  private CompoundDataClass cdc;
  HashMap<String, DataClassBrick> inners;
  private CompoundDataClassBrick(CompoundDataClassBrick outer, CompoundDataClass cdc, HashMap<String, DataClassBrick> inners) {
      super(outer);
      this.cdc = cdc;
      this.inners = inners;
  }
  public static CompoundDataClassBrick make(CompoundDataClassBrick outer, CompoundDataClass cdc, HashMap<String, DataClassBrick> inners) {
      return new CompoundDataClassBrick(outer, cdc, inners);
  }
  public DataClassBrick getInner(String name) {
      return inners.get(name);
  }
  public void putInner(String innerName, DataClassBrick innerVal) {
    inners.put(innerName, innerVal);
  }
  public DataClassBrick calculateInner(String name, DCHolder dcHolder) {
    DataClassBrick inner = getInner(name);
    CompoundDataClass outerDC = getCDC();
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

  public CompoundDataClass getCDC() {
    return cdc;
  }

  public DataClassBrick getOrCalculateInner(String name, CursorPositionDCHolder cursorPositionDCHolder) {
    DataClassBrick inner = getInner(name);
    if(inner != null) {
      if(inner.isComplete()) {
        inner = getCDC().calculateInner(name, this, cursorPositionDCHolder);
      }
    }
    return inner;
  }
}
