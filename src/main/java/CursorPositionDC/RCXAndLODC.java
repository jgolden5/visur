package CursorPositionDC;

import DataClass.*;

import java.util.ArrayList;
import java.util.HashMap;

public class RCXAndLODC extends CompoundDataClass {

  public RCXAndLODC(int requiredSetValues) {
    super(requiredSetValues);
  }

  @Override
  public CompoundDataClassBrick makeBrick(String name, ArrayList<OuterDataClassBrick> outers) {
    CompoundDataClassBrick rcxAndLODCB = CompoundDataClassBrick.make(name, outers, this, new HashMap<>());
    HashMap<String, DataClassBrick> rcxAndLOInners = new HashMap<>();

    WholeNumberDC wholeNumberDC = (WholeNumberDC) getInner("wholeNumber");
    PrimitiveDataClassBrick rcxDCB = wholeNumberDC.makeBrick("rcx", new ArrayList<>());
    PrimitiveDataClassBrick loDCB = wholeNumberDC.makeBrick("lo", new ArrayList<>());

    rcxAndLOInners.put("rcx", rcxDCB);
    rcxAndLOInners.put("lo", loDCB);

    return rcxAndLODCB.getInitializedBrickFromInners(rcxAndLOInners);
  }

  @Override
  public Result<DataClassBrick> calcInternal(String name, DataClassBrick outerAsBrick) {
    return null;
  }

  @Override
  public ConflictsCheckResult conflictsCheck(OuterDataClassBrick thisAsBrick, String targetName, Object targetVal) {
    return null;
  }
}
