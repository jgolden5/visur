package CursorPositionDC;

import DataClass.*;

import java.util.ArrayList;
import java.util.HashMap;

public class VCXAndLLDC extends CompoundDataClass {

  public VCXAndLLDC(int requiredSetValues) {
    super(requiredSetValues);
  }

  @Override
  public CompoundDataClassBrick makeBrick(String name, ArrayList<OuterDataClassBrick> outers, PrimitiveDataClassBrick... reusablePDCBs) {
    CompoundDataClassBrick vcxAndLLDCB = CompoundDataClassBrick.make(name, outers, this, new HashMap<>());
    HashMap<String, DataClassBrick> vcxAndLLInners = new HashMap<>();

    PrimitiveDataClassBrick llDCB = reusablePDCBs[0];

    WholeNumberDC wholeNumberDC = (WholeNumberDC) getInner("wholeNumber");
    PrimitiveDataClassBrick vcxDCB = wholeNumberDC.makeBrick("vcx", new ArrayList<>(), false);
    vcxDCB.putOuter(vcxAndLLDCB);
    llDCB.putOuter(vcxAndLLDCB);

    vcxAndLLInners.put("vcx", vcxDCB);
    vcxAndLLInners.put("ll", llDCB);

    return vcxAndLLDCB.getInitializedBrickFromInners(vcxAndLLInners);
  }

  @Override
  public Result<Object> calcInternal(String targetName, OuterDataClassBrick thisAsBrick) {
    return null;
  }

}
