package DataClass;

import CursorPositionDC.CursorPositionDCHolder;
import com.ple.visur.Result;

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
    if(innerVal != null) {
      innerVal.name = innerName;
    }
    inners.put(innerName, innerVal);
  }

  public CompoundDataClass getCDC() {
    return cdc;
  }

  public Result<DataClassBrick> getOrCalculateInner(String name, CursorPositionDCHolder cursorPositionDCHolder) {
    Result res = Result.make(getInner(name), null);
    DataClassBrick innerTarget = (DataClassBrick) res.getVal();
    if(innerTarget == null || innerTarget instanceof CompoundDataClassBrick) {
      if(isComplete()) {
        res = getCDC().calculateInnerBrick(name, this, cursorPositionDCHolder);
      }
    }
    return res;
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
