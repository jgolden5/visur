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
    PrimitiveDataClassBrick llDCB = reusablePDCBs[1];

    VCXAndLLDC vcxAndLLDC = (VCXAndLLDC) getInner("vcxAndLL");
    CompoundDataClassBrick vcxAndLLDCB = vcxAndLLDC.makeBrick("vcxAndLL", new ArrayList<>(), llDCB);
    vcxAndLLDCB.putOuter(virtualDCB);

    VCXRCXAndLODC vcxRcxAndLODC = (VCXRCXAndLODC) getInner("rcxAndLO");
    CompoundDataClassBrick rcxAndLODCB = vcxRcxAndLODC.makeBrick("rcxAndLO", new ArrayList<>(), rcxDCB);
    rcxAndLODCB.putOuter(virtualDCB);

    virtualInners.put("vcxAndLL", vcxAndLLDCB);
    virtualInners.put("rcxAndLO", rcxAndLODCB);

    return virtualDCB.getInitializedBrickFromInners(virtualInners);
  }

  @Override
  public Result<Object> calcInternal(String targetName, OuterDataClassBrick thisAsBrick) {
    Result<Object> r;
    PrimitiveDataClassBrick[] virtualBricks = getAllVirtualPDCBs(thisAsBrick);
    if (targetName.equals("vcx")) {
      r = calcVCXFromRCXAndLO(virtualBricks);
    } else if(targetName.equals("rcx")) {
      r = calcRCXFromVCXAndLO(virtualBricks);
    } else if(targetName.equals("lo")) {
      r = calcLOFromVCXAndRCX(virtualBricks);
    } else {
      r = Result.make(null, "name not recognized");
    }
    return r;
  }

  private Result<Object> calcVCXFromRCXAndLO(PrimitiveDataClassBrick[] virtualBricks) {
    PrimitiveDataClassBrick vcxDCB = virtualBricks[0];
    PrimitiveDataClassBrick rcxDCB = virtualBricks[1];
    PrimitiveDataClassBrick loDCB = virtualBricks[2];
    int rcx = (int)rcxDCB.getVal();
    int lo = (int)loDCB.getVal();
    int vcx = lo > 0 ? rcx + lo : rcx;
    vcxDCB.cacheVal(vcx);
    return Result.make(vcx, null);
  }

  private Result<Object> calcRCXFromVCXAndLO(PrimitiveDataClassBrick[] virtualBricks) {
    PrimitiveDataClassBrick vcxDCB = virtualBricks[0];
    PrimitiveDataClassBrick rcxDCB = virtualBricks[1];
    PrimitiveDataClassBrick loDCB = virtualBricks[2];
    int lo = (int)loDCB.getVal();
    int vcx = (int)vcxDCB.getVal();
    int rcx = vcx - lo;
    rcxDCB.cacheVal(rcx);
    return Result.make(rcx, null);
  }

  private Result<Object> calcLOFromVCXAndRCX(PrimitiveDataClassBrick[] coordinatesVars) {
    return null;
  }

  private PrimitiveDataClassBrick[] getAllVirtualPDCBs(OuterDataClassBrick thisAsBrick) {
    CompoundDataClassBrick thisAsCDCB = (CompoundDataClassBrick) thisAsBrick;
    CompoundDataClassBrick vcxAndLLDCB = (CompoundDataClassBrick) thisAsCDCB.getInner("vcxAndLL");
    PrimitiveDataClassBrick vcxDCB = (PrimitiveDataClassBrick) vcxAndLLDCB.getInner("vcx");
    CompoundDataClassBrick rcxAndLODCB = (CompoundDataClassBrick) thisAsCDCB.getInner("rcxAndLO");
    PrimitiveDataClassBrick rcxDCB = (PrimitiveDataClassBrick) rcxAndLODCB.getInner("rcx");
    PrimitiveDataClassBrick loDCB = (PrimitiveDataClassBrick) rcxAndLODCB.getInner("lo");
    PrimitiveDataClassBrick llDCB = (PrimitiveDataClassBrick) vcxAndLLDCB.getInner("ll");
    return new PrimitiveDataClassBrick[]{vcxDCB, rcxDCB, loDCB, llDCB};
  }

  private Result<Object> calcVCXAndLL(String name, PrimitiveDataClassBrick[] virtualBricks) {
    PrimitiveDataClassBrick vcxDCB = virtualBricks[0];
    PrimitiveDataClassBrick rcxDCB = virtualBricks[1];
    PrimitiveDataClassBrick loDCB = virtualBricks[2];
    PrimitiveDataClassBrick llDCB = virtualBricks[3];
    int rcx = (int)rcxDCB.getVal();
    int lo = (int)loDCB.getVal();
    int vcx = lo > 0 ? rcx + lo : rcx;
    int ll = vcx - lo;
    Result<Object> r = Result.make();
    if(name.equals("vcx")) {
      r = Result.make(vcx, null);
    } else if(name.equals("ll")) {
      r = Result.make(ll, null);
    }
    vcxDCB.cacheVal(vcx);
    llDCB.cacheVal(ll);
    return r;
  }

  private Result<Object> calcVCXRCXAndLO(String name, PrimitiveDataClassBrick[] virtualBricks) {
    PrimitiveDataClassBrick vcxDCB = virtualBricks[0];
    PrimitiveDataClassBrick rcxDCB = virtualBricks[1];
    PrimitiveDataClassBrick loDCB = virtualBricks[2];
    PrimitiveDataClassBrick llDCB = virtualBricks[3];
    int ll = (int)llDCB.getVal();
    int vcx = lo > 0 ? rcx + lo : rcx;
    int rcx = vcx > ll ? ll : vcx;
    int lo = vcx - ll;

    Result<Object> r = Result.make();
    if(name.equals("vcx")) {
      r = Result.make(vcx, null);
    } else if(name.equals("rcx")) {
      r = Result.make(rcx, null);
    } else if(name.equals("lo")) {
      r = Result.make(lo, null);
    }
    vcxDCB.cacheVal(vcx);
    rcxDCB.cacheVal(rcx);
    loDCB.cacheVal(lo);
    return r;
  }

}
