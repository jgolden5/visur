package CursorPositionDC;

import DataClass.*;

import java.util.ArrayList;
import java.util.HashMap;

public class RCXAndLODC extends CompoundDataClass {

  public RCXAndLODC(int requiredSetValues) {
    super(requiredSetValues);
  }

  @Override
  public CompoundDataClassBrick makeBrick(String name, ArrayList<OuterDataClassBrick> outers, PrimitiveDataClassBrick... reusablePDCBs) {
    CompoundDataClassBrick rcxAndLODCB = CompoundDataClassBrick.make(name, outers, this, new HashMap<>());
    HashMap<String, DataClassBrick> rcxAndLOInners = new HashMap<>();

    PrimitiveDataClassBrick rcxDCB = reusablePDCBs[0];

    rcxDCB.putOuter(rcxAndLODCB);
    WholeNumberDC wholeNumberDC = (WholeNumberDC) getInner("wholeNumber");
    PrimitiveDataClassBrick loDCB = wholeNumberDC.makeBrick("lo", new ArrayList<>());
    loDCB.putOuter(rcxAndLODCB);

    rcxAndLOInners.put("rcx", rcxDCB);
    rcxAndLOInners.put("lo", loDCB);

    return rcxAndLODCB.getInitializedBrickFromInners(rcxAndLOInners);
  }

  @Override
  public Result<DataClassBrick> calcInternal(String name, DataClassBrick outerAsBrick) {
    return null;
  }

}
