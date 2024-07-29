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

    WholeNumberDC wholeNumberDC = (WholeNumberDC) getInner("wholeNumber");
    PrimitiveDataClassBrick vcxDCB = wholeNumberDC.makeBrick("vcx", new ArrayList<>());
    vcxDCB.putOuter(vcxAndLLDCB);
    PrimitiveDataClassBrick llDCB = wholeNumberDC.makeBrick("ll", new ArrayList<>());
    llDCB.putOuter(vcxAndLLDCB);

    vcxAndLLInners.put("vcx", vcxDCB);
    vcxAndLLInners.put("ll", llDCB);

    return vcxAndLLDCB.getInitializedBrickFromInners(vcxAndLLInners);
  }

  @Override
  public Result<DataClassBrick> calcInternal(String name, DataClassBrick outerAsBrick) {
    return null;
  }

}
