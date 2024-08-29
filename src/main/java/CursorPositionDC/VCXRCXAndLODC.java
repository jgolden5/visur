package CursorPositionDC;

import DataClass.*;

import java.util.ArrayList;
import java.util.HashMap;

public class VCXRCXAndLODC extends CompoundDataClass {

  public VCXRCXAndLODC(int requiredSetValues) {
    super(requiredSetValues);
  }

  @Override
  public CompoundDataClassBrick makeBrick(String name, ArrayList<OuterDataClassBrick> outers, PrimitiveDataClassBrick... reusablePDCBs) {
    CompoundDataClassBrick vcxRCXAndLODCB = CompoundDataClassBrick.make(name, outers, this, new HashMap<>());
    HashMap<String, DataClassBrick> vcxRCXAndLOInners = new HashMap<>();

    PrimitiveDataClassBrick rcxDCB = reusablePDCBs[0];

    WholeNumberDC wholeNumberDC = (WholeNumberDC) getInner("wholeNumber");
    PrimitiveDataClassBrick vcxDCB = wholeNumberDC.makeBrick("vcx", new ArrayList<>(), false);
    vcxDCB.putOuter(vcxRCXAndLODCB);
    rcxDCB.putOuter(vcxRCXAndLODCB);
    PrimitiveDataClassBrick loDCB = wholeNumberDC.makeBrick("lo", new ArrayList<>(), false);
    loDCB.putOuter(vcxRCXAndLODCB);

    vcxRCXAndLOInners.put("vcx", vcxDCB);
    vcxRCXAndLOInners.put("rcx", rcxDCB);
    vcxRCXAndLOInners.put("lo", loDCB);

    return vcxRCXAndLODCB.getInitializedBrickFromInners(vcxRCXAndLOInners);
  }

  @Override
  public Result<Object> calcInternal(String targetName, OuterDataClassBrick thisAsBrick) {
    return Result.make(null, "no calculations available at the vcxRCXAnDLODC level");
  }

}
