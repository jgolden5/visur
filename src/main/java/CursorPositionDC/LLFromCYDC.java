package CursorPositionDC;

import DataClass.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Stack;

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
  public Result<Object> calcInternal(Stack<DataClassBrick> innerToOuterBricks) {
    return null;
  }

}
