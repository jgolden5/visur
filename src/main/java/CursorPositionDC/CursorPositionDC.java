package CursorPositionDC;

import DataClass.*;
import DataClass.Result;

import java.util.HashMap;

public class CursorPositionDC extends CompoundDataClass {
  public CursorPositionDC(int minimumRequiredSetValues) {
    super(minimumRequiredSetValues);
  }

  @Override
  public CompoundDataClassBrick makeBrick(String name, CompoundDataClassBrick outer) {
    CompoundDataClassBrick cursorPositionDCB = CompoundDataClassBrick.make(outer, this, new HashMap<>());
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

  @Override
  public CompoundDataClassBrick makeBrick() {
    return null;
  }

  @Override
  public CompoundDataClassBrick makeBrick(CompoundDataClassBrick outer) {
    return makeBrick();
  }

}
