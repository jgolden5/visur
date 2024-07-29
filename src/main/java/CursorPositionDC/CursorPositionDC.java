package CursorPositionDC;

import DataClass.*;
import DataClass.Result;

import java.util.ArrayList;
import java.util.HashMap;

public class CursorPositionDC extends LayeredDataClass {

  public LayeredDataClassBrick makeBrick(PrimitiveDataClassBrick nlDCB, PrimitiveDataClassBrick rcxDCB) {
    return (LayeredDataClassBrick) makeBrick("cursorPosition", null, nlDCB, rcxDCB);
  }

  @Override
  public OuterDataClassBrick makeBrick(String name, ArrayList<OuterDataClassBrick> outers, PrimitiveDataClassBrick... reusablePDCBs) {
    LayeredDataClassBrick cursorPositionDCB = LayeredDataClassBrick.make(name, this, new ArrayList<>());

    PrimitiveDataClassBrick nlDCB = reusablePDCBs[0];
    PrimitiveDataClassBrick rcxDCB = reusablePDCBs[1];

    CoordinatesDC coordinatesDC = (CoordinatesDC) getLayer(0);
    CompoundDataClassBrick coordinatesDCB = coordinatesDC.makeBrick("coordinates", new ArrayList<>(), nlDCB, rcxDCB);
    coordinatesDCB.putOuter(cursorPositionDCB);

    VirtualDC virtualDC = (VirtualDC) getLayer(1);
    CompoundDataClassBrick virtualDCB = virtualDC.makeBrick("virtual", new ArrayList<>(), rcxDCB);
    virtualDCB.putOuter(cursorPositionDCB);

    cursorPositionDCB.putLayer(coordinatesDCB);
    cursorPositionDCB.putLayer(virtualDCB);

    return cursorPositionDCB;
  }

  @Override
  public Result<DataClassBrick> calcInternal(String name, DataClassBrick outerAsBrick) {
    return Result.make();
  }

}
