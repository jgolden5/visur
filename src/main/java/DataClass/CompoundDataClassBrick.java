package DataClass;

import CursorPositionDC.CursorPositionDCHolder;

import java.util.HashMap;

public class CompoundDataClassBrick extends DataClassBrick {
  private CompoundDataClass cdc;
  HashMap<String, DataClassBrick> inners;
  private CompoundDataClassBrick(CompoundDataClassBrick outer, CompoundDataClass cdc, HashMap<String, DataClassBrick> inners) {
      super(cdc, outer);
      this.cdc = cdc;
      this.inners = inners;
  }
  public static CompoundDataClassBrick make(CompoundDataClassBrick outer, CompoundDataClass cdc, HashMap<String, DataClassBrick> inners) {
      return new CompoundDataClassBrick(outer, cdc, inners);
  }
  public DataClassBrick getInner(String name) {
      return inners.get(name);
  }
  public Result putInner(String innerName, DataClassBrick innerVal) {
    String error = null;
    if(innerVal != null) {
      CompoundDataClass thisCDC = getCDC();
      if(!thisCDC.brickCanBeSet(innerName, inners)) {
        error = "putInner failed, too many values set";
      }
      innerVal.name = innerName;
    }
    if(error == null) {
      inners.put(innerName, innerVal);
    }
    return Result.make(null, error);
  }

  public CompoundDataClass getCDC() {
    return cdc;
  }

  public Result<DataClassBrick> getOrCalculateInner(String name, CursorPositionDCHolder cursorPositionDCHolder) {
    DataClassBrick inner = getInner(name);
    Result<DataClassBrick> r;
    //if inner's value is set, return result whose value equals getInner(name)
    if(inner == null) {
      r = Result.make(inner, null);
    } else {
      r = Result.make(inner.calc(), null);
    }
    return r;
  }

  @Override
  public boolean isComplete() {
    int numberOfSetValues = 0;
    for(DataClassBrick inner : inners.values()) {
      if(inner != null && inner.isComplete()) {
        numberOfSetValues++;
      }
    }
    return numberOfSetValues >= cdc.minimumRequiredSetValues;
  }
}
