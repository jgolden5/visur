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
    PrimitiveDataClass wholeNumberDC = (PrimitiveDataClass) getInner("wholeNumber");
    CompoundDataClass coordinatesDC = (CompoundDataClass) getInner("coordinates");

    PrimitiveDataClassBrick niDCB = (PrimitiveDataClassBrick) niDC.makeBrick("ni", cursorPositionDCB);
    cursorPositionDCBInners.put("ni", niDCB);
    PrimitiveDataClassBrick cwDCB = (PrimitiveDataClassBrick) wholeNumberDC.makeBrick("cw", cursorPositionDCB);
    cursorPositionDCBInners.put("cw", cwDCB);
    CompoundDataClassBrick coordinatesDCB = (CompoundDataClassBrick) coordinatesDC.makeBrick("coordinates", cursorPositionDCB);
    cursorPositionDCBInners.put("coordinates", coordinatesDCB);

    return cursorPositionDCB.initInners(cursorPositionDCBInners);
  }

  @Override
  public Result<DataClassBrick> calcInternal(String name, CompoundDataClassBrick outerAsBrick) {
    if("coordinates".contains(name)){
      return outerAsBrick.getCDC().calcInternal(name, (CompoundDataClassBrick) outerAsBrick.getInner("coordinates"));
    }
    return Result.make(null, "incalculable");
  }

  @Override
  public ConflictsCheckResult conflictsCheck(CompoundDataClassBrick brick, String targetName, Object targetVal) {
    return ConflictsCheckResult.no;
  }

}
