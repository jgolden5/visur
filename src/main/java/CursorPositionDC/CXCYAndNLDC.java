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
    CompoundDataClassBrick cxcyAndNLDCB = CompoundDataClassBrick.make(name, outers, this, new HashMap<>());
    HashMap<String, DataClassBrick> cxcyAndNLInners = new HashMap<>();

    WholeNumberDC wholeNumberDC = (WholeNumberDC) getInner("wholeNumber");
    PrimitiveDataClassBrick cxDCB = wholeNumberDC.makeBrick("cx", new ArrayList<>(), false);
    cxDCB.putOuter(cxcyAndNLDCB);

    PrimitiveDataClassBrick cyDCB = wholeNumberDC.makeBrick("cy", new ArrayList<>(), false);
    cyDCB.putOuter(cxcyAndNLDCB);

    PrimitiveDataClassBrick nlDCB = reusablePDCBs[0];

    cxDCB.putOuter(cxcyAndNLDCB);
    cyDCB.putOuter(cxcyAndNLDCB);
    nlDCB.putOuter(cxcyAndNLDCB);

    cxcyAndNLInners.put("cx", cxDCB);
    cxcyAndNLInners.put("cy",  cyDCB);
    cxcyAndNLInners.put("nl", nlDCB);

    return cxcyAndNLDCB.getInitializedBrickFromInners(cxcyAndNLInners);
  }

  @Override
  public Result<Object> calcInternal(String targetName, OuterDataClassBrick thisAsBrick) {
    return null;
  }

}
