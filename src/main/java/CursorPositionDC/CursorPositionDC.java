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
    CompoundDataClassBrick cursorPositionDCB = CompoundDataClassBrick.make(name, null, this, cursorPositionDCBInners);
    PrimitiveDataClass niDC = (PrimitiveDataClass) getInner("ni");
    PrimitiveDataClass cwDC = (PrimitiveDataClass) getInner("cw");
    CompoundDataClass cxcycaDC = (CompoundDataClass) getInner("cxcyca");

    PrimitiveDataClassBrick niDCB = (PrimitiveDataClassBrick) niDC.makeBrick("ni", cursorPositionDCB);
    cursorPositionDCBInners.put("ni", cursorPositionDCB);
    PrimitiveDataClassBrick cwDCB = (PrimitiveDataClassBrick) cwDC.makeBrick("cw", cursorPositionDCB);
    cursorPositionDCBInners.put("cw", cwDCB);
    CompoundDataClassBrick cxcycaDCB = (CompoundDataClassBrick) cxcycaDC.makeBrick("cxcyca", cursorPositionDCB);
    cursorPositionDCBInners.put("cxcyca", cxcycaDCB);

    return cursorPositionDCB.initInners(cursorPositionDCBInners);
  }

  @Override
  public Result<DataClassBrick> calcInternal(String name, CompoundDataClassBrick outerAsBrick) {
    if("cxcyca".contains(name)){
      return outerAsBrick.getCDC().calcInternal(name, (CompoundDataClassBrick) outerAsBrick.getInner("cxcyca"));
    }
    return Result.make(null, "incalculable");
  }

  @Override
  public boolean conflictsCheck(CompoundDataClassBrick brick, String targetName, Object targetVal) {
    return false;
  }

}
