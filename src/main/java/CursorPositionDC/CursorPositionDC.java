package CursorPositionDC;

import DataClass.*;
import DataClass.Result;

import java.util.ArrayList;

public class CursorPositionDC extends LayeredDataClass {

  public LayeredDataClassBrick makeBrick(PrimitiveDataClassBrick... pdcbs) {
    return (LayeredDataClassBrick) makeBrick("cursorPosition", null, pdcbs);
  }

  @Override
  public OuterDataClassBrick makeBrick(String name, ArrayList<OuterDataClassBrick> outers, PrimitiveDataClassBrick... reusablePDCBs) {
    LayeredDataClassBrick cursorPositionDCB = LayeredDataClassBrick.make(name, this, new ArrayList<>());

    PrimitiveDataClassBrick nlDCB = reusablePDCBs[0];

    CoordinatesDC coordinatesDC = (CoordinatesDC) getLayer(0);
    CompoundDataClassBrick coordinatesDCB = coordinatesDC.makeBrick("coordinates", new ArrayList<>(), nlDCB);
    coordinatesDCB.putOuter(cursorPositionDCB);

    cursorPositionDCB.putLayer(coordinatesDCB);

    return cursorPositionDCB;
  }

  @Override
  public Result<Object> calcInternal(String targetName, OuterDataClassBrick thisAsBrick) {
    return Result.make(null, "incalculable");
  }

}
