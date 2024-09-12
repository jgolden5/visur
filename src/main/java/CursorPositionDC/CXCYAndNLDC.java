package CursorPositionDC;

import DataClass.*;

import java.util.ArrayList;
import java.util.HashMap;

public class CXCYAndNLDC extends CompoundDataClass {
  public CXCYAndNLDC(int requiredSetValues) {
    super(requiredSetValues);
  }

  @Override
  public CompoundDataClassBrick makeBrick(String name, ArrayList<OuterDataClassBrick> outers, PrimitiveDataClassBrick... reusablePDCBs) {
    CompoundDataClassBrick rcxcyAndNLDCB = CompoundDataClassBrick.make(name, outers, this, new HashMap<>());
    HashMap<String, DataClassBrick> rcxcyAndNLInners = new HashMap<>();

    WholeNumberDC wholeNumberDC = (WholeNumberDC) getInner("wholeNumber");
    PrimitiveDataClassBrick cxDCB = wholeNumberDC.makeBrick("cx", new ArrayList<>(), false);
    cxDCB.putOuter(rcxcyAndNLDCB);

    PrimitiveDataClassBrick cyDCB = wholeNumberDC.makeBrick("cy", new ArrayList<>(), false);
    cyDCB.putOuter(rcxcyAndNLDCB);

    PrimitiveDataClassBrick nlDCB = reusablePDCBs[0];

    cxDCB.putOuter(rcxcyAndNLDCB);
    cyDCB.putOuter(rcxcyAndNLDCB);
    nlDCB.putOuter(rcxcyAndNLDCB);

    rcxcyAndNLInners.put("cx", cxDCB);
    rcxcyAndNLInners.put("cy",  cyDCB);
    rcxcyAndNLInners.put("nl", nlDCB);

    return rcxcyAndNLDCB.getInitializedBrickFromInners(rcxcyAndNLInners);
  }

  @Override
  public Result<Object> calcInternal(String targetName, OuterDataClassBrick thisAsBrick) {
    return null;
  }

}
