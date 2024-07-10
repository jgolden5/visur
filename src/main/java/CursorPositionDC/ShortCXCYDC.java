package CursorPositionDC;

import DataClass.*;

import java.util.HashMap;

public class ShortCXCYDC extends CompoundDataClass {
  public ShortCXCYDC(int minimumRequiredSetValues) {
    super(minimumRequiredSetValues);
  }

  @Override
  public DataClassBrick makeBrick() {
    return null;
  }

  @Override
  public DataClassBrick makeBrick(String name, CompoundDataClassBrick outer) {
    HashMap<String, DataClassBrick> shortCXCYDCBInners = new HashMap<>();
    CompoundDataClassBrick shortCXCYDCB = CompoundDataClassBrick.make(name, outer, this, shortCXCYDCBInners);
    PrimitiveDataClass wholeNumberDC = (PrimitiveDataClass) getInner("wholeNumber");
    PrimitiveDataClassBrick shortCXDCB = (PrimitiveDataClassBrick) wholeNumberDC.makeBrick("shortCX", shortCXCYDCB);
    shortCXCYDCBInners.put("shortCX", shortCXDCB);
    PrimitiveDataClassBrick shortCYDCB = (PrimitiveDataClassBrick) wholeNumberDC.makeBrick("shortCY", shortCXCYDCB);
    shortCXCYDCBInners.put("shortCY", shortCYDCB);
    return shortCXCYDCB.initInners(shortCXCYDCBInners);
  }

  @Override
  public Result<DataClassBrick> calcInternal(String name, CompoundDataClassBrick outerAsBrick) {
    return null;
  }

  @Override
  public boolean conflictsCheck(CompoundDataClassBrick brick, String targetName, Object targetVal) {
    return false;
  }

}
