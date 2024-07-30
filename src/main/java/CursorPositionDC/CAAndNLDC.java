package CursorPositionDC;

import DataClass.*;

import java.util.ArrayList;
import java.util.HashMap;

public class CAAndNLDC extends CompoundDataClass {
  public CAAndNLDC(int requiredSetValues) {
    super(requiredSetValues);
  }

  @Override
  public CompoundDataClassBrick makeBrick(String name, ArrayList<OuterDataClassBrick> outers, PrimitiveDataClassBrick... reusablePDCBs) {
    CompoundDataClassBrick caAndNLDCB = CompoundDataClassBrick.make(name, outers, this, new HashMap<>());
    HashMap<String, DataClassBrick> caAndNLInners = new HashMap<>();

    PrimitiveDataClassBrick nlDCB = reusablePDCBs[0];

    WholeNumberDC wholeNumberDC = (WholeNumberDC) getInner("wholeNumber");
    PrimitiveDataClassBrick caDCB = wholeNumberDC.makeBrick("ca", new ArrayList<>(), false);
    caDCB.putOuter(caAndNLDCB);

    nlDCB.putOuter(caAndNLDCB);

    caAndNLInners.put("ca", caDCB);
    caAndNLInners.put("nl", nlDCB);

    return caAndNLDCB.getInitializedBrickFromInners(caAndNLInners);
  }

  @Override
  public Result<PrimitiveDataClassBrick> calcInternal(String name, OuterDataClassBrick thisAsBrick) {
    ArrayList<OuterDataClassBrick> outers = thisAsBrick.getOuters();
    OuterDataClassBrick coordinatesDCB = outers.get(0);
    CoordinatesDC coordinatesDC = (CoordinatesDC)coordinatesDCB.dc;
    return coordinatesDC.calcInternal(name, coordinatesDCB);
  }

}
