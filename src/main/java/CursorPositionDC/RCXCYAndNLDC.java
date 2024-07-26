package CursorPositionDC;

import DataClass.*;

import java.util.ArrayList;
import java.util.HashMap;

public class RCXCYAndNLDC extends CompoundDataClass {
  public RCXCYAndNLDC(int requiredSetValues) {
    super(requiredSetValues);
  }

  @Override
  public CompoundDataClassBrick makeBrick(String name, ArrayList<OuterDataClassBrick> outers) {
    CompoundDataClassBrick rcxcyAndNLDCB = CompoundDataClassBrick.make(name, outers, this, new HashMap<>());
    HashMap<String, DataClassBrick> rcxcyAndNLInners = new HashMap<>();

    WholeNumberDC wholeNumberDC = (WholeNumberDC) getInner("wholeNumber");
    PrimitiveDataClassBrick rcxDCB = wholeNumberDC.makeBrick("rcx", new ArrayList<>());
    rcxDCB.putOuter(rcxcyAndNLDCB);
    PrimitiveDataClassBrick cyDCB = wholeNumberDC.makeBrick("cy", new ArrayList<>());
    cyDCB.putOuter(rcxcyAndNLDCB);

    WholeNumberListDC nlDC = (WholeNumberListDC) getInner("nl");
    PrimitiveDataClassBrick nlDCB = nlDC.makeBrick("nl", new ArrayList<>());
    nlDCB.putOuter(rcxcyAndNLDCB);

    rcxcyAndNLInners.put("rcx", rcxDCB);
    rcxcyAndNLInners.put("cy",  cyDCB);
    rcxcyAndNLInners.put("nl", nlDCB);

    return rcxcyAndNLDCB.getInitializedBrickFromInners(rcxcyAndNLInners);
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
