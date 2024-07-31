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
      int[] coordinatesVars = getAllCoordinatePDCBVals(thisAsBrick);
      CompoundDataClassBrick thisAsCDCB = (CompoundDataClassBrick) thisAsBrick;
      if (name.equals("vcx") || name.equals("ll")) {
        r = calcVCXAndLL(name, thisAsCDCB, coordinatesVars);
      } else if (name.equals("rcx") || name.equals("lo")) {
        r = calcRCXAndLO(name, thisAsCDCB, coordinatesVars);
      } else {
        r = Result.make(null, "name not recognized");
      }
    } else {
      r = Result.make(null, "brick incomplete, calculations impossible");
    }
    return r;
  }

  private int[] getAllCoordinatePDCBVals(OuterDataClassBrick thisAsBrick) {
    CompoundDataClassBrick thisAsCDCB = (CompoundDataClassBrick)thisAsBrick;
    CompoundDataClassBrick vcxAndLLDCB = (CompoundDataClassBrick) thisAsCDCB.getInner("vcxAndLL");
    PrimitiveDataClassBrick vcxDCB = (PrimitiveDataClassBrick) vcxAndLLDCB.getInner("vcx");
    PrimitiveDataClassBrick llDCB = (PrimitiveDataClassBrick) vcxAndLLDCB.getInner("ll");
    CompoundDataClassBrick rcxAndLODCB = (CompoundDataClassBrick) thisAsCDCB.getInner("rcxAndLO");
    PrimitiveDataClassBrick rcxDCB = (PrimitiveDataClassBrick) rcxAndLODCB.getInner("rcx");
    PrimitiveDataClassBrick loDCB = (PrimitiveDataClassBrick) rcxAndLODCB.getInner("lo");
    int vcx = vcxDCB.isComplete() ? (int)vcxDCB.getVal() : -1;
    int ll = llDCB.isComplete() ? (int)llDCB.getVal() : -1;
    int rcx = rcxDCB.isComplete() ?  (int)rcxDCB.getVal() : -1;
    int lo = loDCB.isComplete() ? (int)loDCB.getVal() : -1;
    return new int[]{vcx, ll, rcx, lo};
  }

  private Result<PrimitiveDataClassBrick> calcVCXAndLL(String name, CompoundDataClassBrick thisAsCDCB, int[] coordinatesVars) {
    return null;
  }

  private Result<PrimitiveDataClassBrick> calcRCXAndLO(String name, CompoundDataClassBrick thisAsCDCB, int[] coordinatesVars) {
    int vcx = coordinatesVars[0];
    int ll = coordinatesVars[1];
    int rcx = vcx > ll ? ll : vcx;
    int lo = vcx - ll;
    CompoundDataClassBrick rcxAndLODCB = (CompoundDataClassBrick) thisAsCDCB.getInner("rcxAndLO");
    PrimitiveDataClassBrick rcxDCB = (PrimitiveDataClassBrick) rcxAndLODCB.getInner("rcx");
    rcxDCB.put(rcx);
    PrimitiveDataClassBrick loDCB = (PrimitiveDataClassBrick) rcxAndLODCB.getInner("lo");
    loDCB.put(lo);

    PrimitiveDataClassBrick brickResult = null;
    if(name.equals("rcx")) {
      brickResult = rcxDCB;
    } else if(name.equals("lo")) {
      brickResult = loDCB;
    }
    return Result.make(brickResult, null);
  }

}
