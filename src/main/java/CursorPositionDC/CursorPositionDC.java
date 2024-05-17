package CursorPositionDC;

import DataClass.*;
import DataClass.Result;

import java.util.HashMap;

public class CursorPositionDC extends CompoundDataClass {
  public CursorPositionDC(int minimumRequiredSetValues) {
    super(minimumRequiredSetValues);
  }

  @Override
  public CompoundDataClassBrick makeBrick() {
    return makeBrick("cursorPosition", null);
  }

  @Override
  public CompoundDataClassBrick makeBrick(String name, CompoundDataClassBrick outer) {
    HashMap<String, DataClassBrick> inners = new HashMap<>();
    CompoundDataClassBrick cursorPositionDCB = CompoundDataClassBrick.make(null, this, inners);
    getInner("ni").makeBrick("ni", cursorPositionDCB);
    inners.put("ni", niDCB);
    getInner("cxcyca").makeBrick("ni", cursorPositionDCB);
    inners.put("cxcyca", cxcycaDCB);
    cursorPositionDCB = CompoundDataClassBrick.make(null, this, inners);
    cursorPositionDCB.putName(name);
    return cursorPositionDCB;
  }

  @Override
  public boolean conflicts(CompoundDataClassBrick brick) {
    return false;
  }

  @Override
  public Result<DataClassBrick> calcInternal(String name, CompoundDataClassBrick outerAsBrick) {
    if("cxcyca".contains(name)){
      CursorPositionDCHolder cursorPositionDCHolder = (CursorPositionDCHolder) dcHolder;
      return cursorPositionDCHolder.cxcycaDC.calcInternal(name, (CompoundDataClassBrick) outerAsBrick.getInner("cxcyca"), dcHolder);
    }
    return Result.make(null, "incalculable");
  }

}
