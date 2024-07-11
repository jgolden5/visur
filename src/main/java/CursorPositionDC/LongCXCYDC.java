package CursorPositionDC;

import DataClass.*;

import java.util.HashMap;

public class LongCXCYDC extends CompoundDataClass {
  public LongCXCYDC(int minimumRequiredSetValues) {
    super(minimumRequiredSetValues);
  }

  @Override
  public DataClassBrick makeBrick() {
    return null;
  }

  @Override
  public DataClassBrick makeBrick(String name, CompoundDataClassBrick outer) {
    HashMap<String, DataClassBrick> longCXCYDCBInners = new HashMap<>();
    CompoundDataClassBrick longCXCYDCB = CompoundDataClassBrick.make(name, outer, this, longCXCYDCBInners);
    PrimitiveDataClass wholeNumberDC = (PrimitiveDataClass) getInner("wholeNumber");
    PrimitiveDataClassBrick longCXDCB = (PrimitiveDataClassBrick) wholeNumberDC.makeBrick("longCX", longCXCYDCB);
    longCXCYDCBInners.put("longCX", longCXDCB);
    PrimitiveDataClassBrick longCYDCB = (PrimitiveDataClassBrick) wholeNumberDC.makeBrick("longCY", longCXCYDCB);
    longCXCYDCBInners.put("longCY", longCYDCB);
    return longCXCYDCB.initInners(longCXCYDCBInners);
  }

  @Override
  public Result<DataClassBrick> calcInternal(String name, CompoundDataClassBrick outerAsBrick) {
    return null;
  }

  @Override
  public ConflictsCheckResult conflictsCheck(CompoundDataClassBrick brick, String targetName, Object targetVal) {
    return ConflictsCheckResult.no;
  }

}
