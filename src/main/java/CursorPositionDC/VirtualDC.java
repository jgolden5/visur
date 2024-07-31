package CursorPositionDC;

import DataClass.*;

import java.util.ArrayList;
import java.util.HashMap;

public class VirtualDC extends CompoundDataClass {

  public VirtualDC(int requiredSetValues) {
    super(requiredSetValues);
  }

  @Override
  public CompoundDataClassBrick makeBrick(String name, ArrayList<OuterDataClassBrick> outers, PrimitiveDataClassBrick... reusablePDCBs) {
    CompoundDataClassBrick virtualDCB = CompoundDataClassBrick.make(name, outers, this, new HashMap<>());
    HashMap<String, DataClassBrick> virtualInners = new HashMap<>();

    PrimitiveDataClassBrick rcxDCB = reusablePDCBs[0];

    VCXAndLLDC vcxAndLLDC = (VCXAndLLDC) getInner("vcxAndLL");
    CompoundDataClassBrick vcxAndLLDCB = vcxAndLLDC.makeBrick("vcxAndLL", new ArrayList<>());
    vcxAndLLDCB.putOuter(virtualDCB);

    RCXAndLODC rcxAndLODC = (RCXAndLODC) getInner("rcxAndLO");
    CompoundDataClassBrick rcxAndLODCB = rcxAndLODC.makeBrick("rcxAndLO", new ArrayList<>(), rcxDCB);
    rcxAndLODCB.putOuter(virtualDCB);

    virtualInners.put("vcxAndLL", vcxAndLLDCB);
    virtualInners.put("rcxAndLO", rcxAndLODCB);

    return virtualDCB.getInitializedBrickFromInners(virtualInners);
  }

  @Override
  public Result<PrimitiveDataClassBrick> calcInternal(String name, OuterDataClassBrick thisAsBrick) {
    Result<PrimitiveDataClassBrick> r;
    if(thisAsBrick.isComplete()) {
      Object[] coordinatesVars = getAllCoordinatePDCBVals(thisAsBrick);
      CompoundDataClassBrick thisAsCDCB = (CompoundDataClassBrick) thisAsBrick;
      if (name.equals("vcx") || name.equals("ll")) {
        r = calcVCXAndLL(thisAsCDCB, coordinatesVars);
      } else if (name.equals("rcx") || name.equals("lo")) {
        r = calcRCXAndLO(thisAsCDCB, coordinatesVars);
      } else {
        r = Result.make(null, "name not recognized");
      }
    } else {
      r = Result.make(null, "brick incomplete, calculations impossible");
    }
    return r;
  }

  private Object[] getAllCoordinatePDCBVals(OuterDataClassBrick thisAsBrick) {
    return new Object[0];
  }

  private Result<PrimitiveDataClassBrick> calcVCXAndLL(CompoundDataClassBrick thisAsCDCB, Object[] coordinatesVars) {
    return null;
  }

  private Result<PrimitiveDataClassBrick> calcRCXAndLO(CompoundDataClassBrick thisAsCDCB, Object[] coordinatesVars) {
    return null;
  }

}
