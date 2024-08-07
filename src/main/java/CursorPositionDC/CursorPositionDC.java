package CursorPositionDC;

import DataClass.*;
import DataClass.Result;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Stack;

public class CursorPositionDC extends LayeredDataClass {

  public LayeredDataClassBrick makeBrick(PrimitiveDataClassBrick... pdcbs) {
    return (LayeredDataClassBrick) makeBrick("cursorPosition", null, pdcbs);
  }

  @Override
  public OuterDataClassBrick makeBrick(String name, ArrayList<OuterDataClassBrick> outers, PrimitiveDataClassBrick... reusablePDCBs) {
    LayeredDataClassBrick cursorPositionDCB = LayeredDataClassBrick.make(name, this, new ArrayList<>());

    PrimitiveDataClassBrick nlDCB = reusablePDCBs[0];
    PrimitiveDataClassBrick rcxDCB = reusablePDCBs[1];
    PrimitiveDataClassBrick cyDCB = reusablePDCBs[2];
    PrimitiveDataClassBrick llDCB = reusablePDCBs[3];

    CoordinatesDC coordinatesDC = (CoordinatesDC) getLayer(0);
    CompoundDataClassBrick coordinatesDCB = coordinatesDC.makeBrick("coordinates", new ArrayList<>(), nlDCB, rcxDCB, cyDCB);
    coordinatesDCB.putOuter(cursorPositionDCB);

    LLFromCYDC llFromCYDC = (LLFromCYDC) getLayer(1);
    CompoundDataClassBrick llFromCYDCB = llFromCYDC.makeBrick("llFromCY", new ArrayList<>(), nlDCB, cyDCB, llDCB);

    VirtualDC virtualDC = (VirtualDC) getLayer(2);
    CompoundDataClassBrick virtualDCB = virtualDC.makeBrick("virtual", new ArrayList<>(), rcxDCB, llDCB);
    virtualDCB.putOuter(cursorPositionDCB);

    cursorPositionDCB.putLayer(coordinatesDCB);
    cursorPositionDCB.putLayer(llFromCYDCB);
    cursorPositionDCB.putLayer(virtualDCB);

    return cursorPositionDCB;
  }

  @Override
  public Result<Object> calcInternal(Stack<String> innerToOuterBrickNames, OuterDataClassBrick thisAsBrick) {
    return Result.make(null, "incalculable");
  }

}
