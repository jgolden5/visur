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
    Result res;
    if(cdc.checkCanSet(this, outer, cursorPositionDCHolder)) {
      res = Result.make(getInner(name), null);
      DataClassBrick innerTarget = (DataClassBrick) res.getVal();
      if (innerTarget == null || innerTarget instanceof CompoundDataClassBrick) {
        if (isComplete()) {
          res = getCDC().calculateInnerBrick(name, this, cursorPositionDCHolder);
        }
      }
    } else {
      res = Result.make(null,"minimum required values not set");
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
