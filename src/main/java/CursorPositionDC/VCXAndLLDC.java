package CursorPositionDC;

import DataClass.*;

import java.util.ArrayList;
import java.util.HashMap;

public class VCXAndLLDC extends CompoundDataClass {

  public VCXAndLLDC(int requiredSetValues) {
    super(requiredSetValues);
  }

  @Override
  public CompoundDataClassBrick makeBrick(String name, ArrayList<OuterDataClassBrick> outers) {
    CompoundDataClassBrick vcxAndLLDCB = CompoundDataClassBrick.make(name, outers, this, new HashMap<>());
    HashMap<String, DataClassBrick> vcxAndLLInners = new HashMap<>();

    WholeNumberDC wholeNumberDC = (WholeNumberDC) getInner("wholeNumber");
    PrimitiveDataClassBrick vcxDCB = wholeNumberDC.makeBrick("vcx", new ArrayList<>());
    PrimitiveDataClassBrick llDCB = wholeNumberDC.makeBrick("ll", new ArrayList<>());

    vcxAndLLInners.put("vcx", vcxDCB);
    vcxAndLLInners.put("ll", llDCB);

    return vcxAndLLDCB.getInitializedBrickFromInners(vcxAndLLInners);
  }

  @Override
  public Result<DataClassBrick> calcInternal(String name, DataClassBrick outerAsBrick) {
    return null;
  }

  @Override
  public ConflictsCheckResult conflictsCheck(OuterDataClassBrick thisAsBrick, String targetName, Object targetVal) {
    return null;
  }

}
