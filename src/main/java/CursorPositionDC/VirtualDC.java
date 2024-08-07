package CursorPositionDC;

import DataClass.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Stack;

public class VirtualDC extends CompoundDataClass {

  public VirtualDC(int requiredSetValues) {
    super(requiredSetValues);
  }

  @Override
  public CompoundDataClassBrick makeBrick(String name, ArrayList<OuterDataClassBrick> outers, PrimitiveDataClassBrick... reusablePDCBs) {
    CompoundDataClassBrick virtualDCB = CompoundDataClassBrick.make(name, outers, this, new HashMap<>());
    HashMap<String, DataClassBrick> virtualInners = new HashMap<>();

    PrimitiveDataClassBrick rcxDCB = reusablePDCBs[0];
    PrimitiveDataClassBrick llDCB = reusablePDCBs[1];

    VCXAndLLDC vcxAndLLDC = (VCXAndLLDC) getInner("vcxAndLL");
    CompoundDataClassBrick vcxAndLLDCB = vcxAndLLDC.makeBrick("vcxAndLL", new ArrayList<>(), llDCB);
    vcxAndLLDCB.putOuter(virtualDCB);

    RCXAndLODC rcxAndLODC = (RCXAndLODC) getInner("rcxAndLO");
    CompoundDataClassBrick rcxAndLODCB = rcxAndLODC.makeBrick("rcxAndLO", new ArrayList<>(), rcxDCB);
    rcxAndLODCB.putOuter(virtualDCB);

    virtualInners.put("vcxAndLL", vcxAndLLDCB);
    virtualInners.put("rcxAndLO", rcxAndLODCB);

    return virtualDCB.getInitializedBrickFromInners(virtualInners);
  }

  @Override
  public Result<Object> calcInternal(Stack<String> innerToOuterBrickNames, OuterDataClassBrick thisAsBrick) {
    Result<Object> r;
    int[] coordinatesVars = getAllCoordinatePDCBVals(thisAsBrick);
    CompoundDataClassBrick thisAsCDCB = (CompoundDataClassBrick) thisAsBrick;
    String name = innerToOuterBrickNames.pop();
    if (name.equals("vcxAndLL")) {
      r = calcVCXAndLL(innerToOuterBrickNames.pop(), thisAsCDCB, coordinatesVars);
    } else if (name.equals("rcxAndLO")) {
      r = calcRCXAndLO(innerToOuterBrickNames.pop(), thisAsCDCB, coordinatesVars);
    } else {
      r = Result.make(null, "name not recognized");
    }
    return r;
    /*
    Result<Object> r;
    Object[] coordinatesVars = getAllCoordinatePDCBVals(thisAsBrick);
    CompoundDataClassBrick thisAsCDCB = (CompoundDataClassBrick) thisAsBrick;
    String name = innerToOuterBrickNames.pop();
    if (name.equals("caAndNL")) {
      r = calcCAAndNL(thisAsCDCB, coordinatesVars);
    } else if (name.equals("rcxcyAndNL")) {
      r = calcRCXCYAndNL(innerToOuterBrickNames.pop(), thisAsCDCB, coordinatesVars);
    } else {
      r = Result.make(null, "name not recognized");
    }
    return r;
     */
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

  private Result<Object> calcVCXAndLL(String name, CompoundDataClassBrick thisAsCDCB, int[] coordinatesVars) {
    int rcx = coordinatesVars[2];
    int lo = coordinatesVars[3];
    int vcx = lo > 0 ? rcx + lo : rcx;
    int ll = vcx - lo;

    Result<Object> r = Result.make();
    if(name.equals("vcx")) {
      r = Result.make(vcx, null);
    } else if(name.equals("ll")) {
      r = Result.make(ll, null);
    }
    return r;
  }

  private Result<Object> calcRCXAndLO(String name, CompoundDataClassBrick thisAsCDCB, int[] coordinatesVars) {
    int vcx = coordinatesVars[0];
    int ll = coordinatesVars[1];
    int rcx = vcx > ll ? ll : vcx;
    int lo = vcx - ll;

    Result<Object> r = Result.make();
    if(name.equals("rcx")) {
      r = Result.make(rcx, null);
    } else if(name.equals("lo")) {
      r = Result.make(lo, null);
    }
    return r;
  }

}
