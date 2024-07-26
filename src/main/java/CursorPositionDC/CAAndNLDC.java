package CursorPositionDC;

import DataClass.*;

import java.util.ArrayList;
import java.util.HashMap;

public class CAAndNLDC extends CompoundDataClass {
  public CAAndNLDC(int requiredSetValues) {
    super(requiredSetValues);
  }

  @Override
  public CompoundDataClassBrick makeBrick(String name, ArrayList<OuterDataClassBrick> outers) {
    CompoundDataClassBrick caAndNLDCB = CompoundDataClassBrick.make(name, outers, this, new HashMap<>());
    HashMap<String, DataClassBrick> caAndNLInners = new HashMap<>();

    WholeNumberDC wholeNumberDC = (WholeNumberDC) getInner("wholeNumber");
    PrimitiveDataClassBrick caDCB = wholeNumberDC.makeBrick("ca", new ArrayList<>());
    caDCB.putOuter(caAndNLDCB);

    WholeNumberListDC nlDC = (WholeNumberListDC) getInner("nl");
    PrimitiveDataClassBrick nlDCB = nlDC.makeBrick("nl", new ArrayList<>());
    nlDCB.putOuter(caAndNLDCB);

    caAndNLInners.put("ca", caDCB);
    caAndNLInners.put("nl", nlDCB);

    return caAndNLDCB.getInitializedBrickFromInners(caAndNLInners);
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
