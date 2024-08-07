package CursorPositionDC;

import DataClass.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Stack;

public class CYAndNLDC extends CompoundDataClass {
  public CYAndNLDC(int requiredSetValues) {
    super(requiredSetValues);
  }

  @Override
  public CompoundDataClassBrick makeBrick(String name, ArrayList<OuterDataClassBrick> outers, PrimitiveDataClassBrick... reusablePDCBs) {
    CompoundDataClassBrick cyAndNLDCB = CompoundDataClassBrick.make(name, outers, this, new HashMap<>());
    HashMap<String, DataClassBrick> cyAndNLInners = new HashMap<>();

    PrimitiveDataClassBrick nlDCB = reusablePDCBs[0];
    PrimitiveDataClassBrick cyDCB = reusablePDCBs[1];

    nlDCB.putOuter(cyAndNLDCB);
    cyDCB.putOuter(cyAndNLDCB);

    cyAndNLInners.put("nl", nlDCB);
    cyAndNLInners.put("cy", cyDCB);

    return cyAndNLDCB.getInitializedBrickFromInners(cyAndNLInners);
  }

  @Override
  public Result<Object> calcInternal(Stack<DataClassBrick> innerToOuterBricks) {
    return null;
  }
}
