package CursorPositionDC;

import DataClass.*;

import java.util.HashMap;

public class CXCYDC extends CompoundDataClass {

  public CXCYDC(int minimumRequiredSetValues) {
    super(minimumRequiredSetValues);
  }

  @Override
  public DataClassBrick makeBrick() {
    return null;
  }

  @Override
  public DataClassBrick makeBrick(String name, CompoundDataClassBrick outer) {
    HashMap<String, DataClassBrick> cxcyDCBInners = new HashMap<>();
    CompoundDataClassBrick cxcyDCB = CompoundDataClassBrick.make(name, outer, this, cxcyDCBInners);
    CompoundDataClass longCXCYDC = (CompoundDataClass) getInner("longCXCY");
    CompoundDataClassBrick longCXCYDCB = (CompoundDataClassBrick) longCXCYDC.makeBrick("longCXCY", cxcyDCB);
    cxcyDCBInners.put("longCXCY", longCXCYDCB);
    CompoundDataClass shortCXCYDC = (CompoundDataClass) getInner("shortCXCY");
    CompoundDataClassBrick shortCXCYDCB = (CompoundDataClassBrick) shortCXCYDC.makeBrick("shortCXCY", cxcyDCB);
    cxcyDCBInners.put("shortCXCY", shortCXCYDCB);
    return cxcyDCB.initInners(cxcyDCBInners);
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
