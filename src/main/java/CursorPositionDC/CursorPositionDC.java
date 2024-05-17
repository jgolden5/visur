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
    HashMap<String, DataClassBrick> cursorPositionDCBInners = new HashMap<>();
    CompoundDataClassBrick cursorPositionDCB = CompoundDataClassBrick.make(null, this, cursorPositionDCBInners);
    cursorPositionDCB.putName(name);
    PrimitiveDataClass niDC = (PrimitiveDataClass) getInner("ni");
    PrimitiveDataClassBrick niDCB = (PrimitiveDataClassBrick) niDC.makeBrick("ni", cursorPositionDCB);
    cursorPositionDCBInners.put("ni", niDCB);
    PrimitiveDataClassBrick cxcycaDCB = (PrimitiveDataClassBrick) getInner("cxcyca").makeBrick("cxcyca", cursorPositionDCB);
    cursorPositionDCBInners.put("cxcyca", cxcycaDCB);
    return cursorPositionDCB.initInners(cursorPositionDCBInners);
  }

  @Override
  public boolean conflicts(CompoundDataClassBrick brick) {
    return false;
  }

  @Override
  public Result<DataClassBrick> calcInternal(String name, CompoundDataClassBrick outerAsBrick) {
    if("cxcyca".contains(name)){
      return outerAsBrick.getCDC().calcInternal(name, (CompoundDataClassBrick) outerAsBrick.getInner("cxcyca"));
    }
    return Result.make(null, "incalculable");
  }

}
