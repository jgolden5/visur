package CursorPositionDC;

import DataClass.*;

import java.util.ArrayList;
import java.util.HashMap;

public class LLFromCYDC extends CompoundDataClass {
  public LLFromCYDC(int requiredSetValues) {
    super(requiredSetValues);
  }

  @Override
  public CompoundDataClassBrick makeBrick(String name, ArrayList<OuterDataClassBrick> outers, PrimitiveDataClassBrick... reusablePDCBs) {
    CompoundDataClassBrick llFromCYDCB = CompoundDataClassBrick.make(name, outers, this, new HashMap<>());
    HashMap<String, DataClassBrick> llFromCYInners = new HashMap<>();

    PrimitiveDataClassBrick nlDCB = reusablePDCBs[0];
    PrimitiveDataClassBrick cyDCB = reusablePDCBs[1];
    PrimitiveDataClassBrick llDCB = reusablePDCBs[2];

    CYAndNLDC cyAndNLDC = (CYAndNLDC) getInner("cyAndNL");
    CompoundDataClassBrick cyAndNLDCB = cyAndNLDC.makeBrick("cyAndNL", new ArrayList<>(), nlDCB, cyDCB);
    cyAndNLDCB.putOuter(llFromCYDCB);

    LLCYAndNLDC llcyAndNLDC = (LLCYAndNLDC) getInner("llcyAndNL");
    CompoundDataClassBrick llcyAndNLDCB = llcyAndNLDC.makeBrick("llcyAndNL", new ArrayList<>(), nlDCB, cyDCB, llDCB);
    llcyAndNLDCB.putOuter(llFromCYDCB);

    llFromCYInners.put("cyAndNL", cyAndNLDCB);
    llFromCYInners.put("llcyAndNL", llcyAndNLDCB);

    return llFromCYDCB.getInitializedBrickFromInners(llFromCYInners);
  }

  @Override
  public Result<Object> calcInternal(String targetName, OuterDataClassBrick thisAsBrick) {
    Result<Object> r;
    PrimitiveDataClassBrick[] llFromCYPDCBs = getLLFromCYPDCBs(thisAsBrick);
    if (targetName.equals("ll")) {
      r = calcLL(llFromCYPDCBs);
    } else {
      r = Result.make(null, "name not recognized");
    }
    return r;
  }

  private Result<Object> calcLL(PrimitiveDataClassBrick[] llFromCYBricks) {
    PrimitiveDataClassBrick llDCB = llFromCYBricks[0];
    PrimitiveDataClassBrick cyDCB = llFromCYBricks[1];
    PrimitiveDataClassBrick nlDCB = llFromCYBricks[2];
    int cy = cyDCB.isComplete() ? (int)cyDCB.getVal() : 0;
    ArrayList<Integer> nl = nlDCB.isComplete() ? (ArrayList<Integer>) nlDCB.getVal() : null;
    Result<Object> r;
    if(cy < nl.size()) {
      int lineStart = cy > 0 ? nl.get(cy - 1) : 0;
      int ll = nl.get(cy) - lineStart;
      r = Result.make(ll, null);
      llDCB.cacheVal(ll);
    } else {
      r = Result.make(null, "cy is too big for nl");
    }
    return r;
  }

  private PrimitiveDataClassBrick[] getLLFromCYPDCBs(OuterDataClassBrick thisAsBrick) {
    CompoundDataClassBrick thisAsCDCB = (CompoundDataClassBrick) thisAsBrick;
    CompoundDataClassBrick llcyAndNLDCB = (CompoundDataClassBrick) thisAsCDCB.getInner("llcyAndNL");
    PrimitiveDataClassBrick llDCB = (PrimitiveDataClassBrick) llcyAndNLDCB.getInner("ll");
    PrimitiveDataClassBrick cyDCB = (PrimitiveDataClassBrick) llcyAndNLDCB.getInner("cy");
    PrimitiveDataClassBrick nlDCB = (PrimitiveDataClassBrick) llcyAndNLDCB.getInner("nl");
    return new PrimitiveDataClassBrick[]{llDCB, cyDCB, nlDCB};
  }

}
