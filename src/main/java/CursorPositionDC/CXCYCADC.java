package CursorPositionDC;

import DataClass.*;
import DataClass.Result;

import java.util.ArrayList;
import java.util.HashMap;

public class CXCYCADC extends CompoundDataClass {
  public CXCYCADC(int minimumRequiredSetValues) {
    super(minimumRequiredSetValues);
  }

  @Override
  public CompoundDataClassBrick makeBrick(DCHolder dcHolder, CompoundDataClassBrick outer) {
    return CompoundDataClassBrick.make(outer, this, new HashMap<>());
  }

  @Override
  public Result<DataClassBrick> calcInternal(DataClassBrick dcb) {
    Result<DataClassBrick> r;
    switch(dcb.name) {
      case "cxcy", "cx", "cy":
        r.putVal(calculateCXCY());
        break;
      case "ca":
        r.putVal(calculateCA());
        break;
      default:
        r.putError("name not recognized");
    }
    return r;
  }

  private Result<DataClassBrick> calculateCXCY(ArrayList<Integer> newlineIndices, CompoundDataClassBrick cxcyDCB, CursorPositionDCHolder cursorPositionDCHolder) {
    PrimitiveDataClassBrick aDCB = (PrimitiveDataClassBrick) cxcyDCB.getInner("a");
    int a = (int)aDCB.getDFB().getVal();
    int cx;
    int cy = 0;
    for(int i = 0; i < newlineIndices.size(); i++) {
      if(a > newlineIndices.get(i)) {
        cy++;
      } else {
        break;
      }
    }
    if(cy > 0) {
      cx = a - (newlineIndices.get(cy - 1) + 1);
    } else {
      cx = a;
    }
    CompoundDataClassBrick cxcyDCB = cursorPositionDCHolder.wholePairDC.makeBrick(cursorPositionDCHolder, cxcyDCB);
    PrimitiveDataClassBrick cxDCB = cursorPositionDCHolder.wholeNumberDC.makeBrick(cx, cxcyDCB, cursorPositionDCHolder);
    PrimitiveDataClassBrick cyDCB = cursorPositionDCHolder.wholeNumberDC.makeBrick(cy, cxcyDCB, cursorPositionDCHolder);
    cxcyDCB.putInner("cx", cxDCB);
    cxcyDCB.putInner("cy", cyDCB);
    return Result.make(cxcyDCB, null);
  }

  private Result<DataClassBrick> calculateCA(ArrayList<Integer> newlineIndices, CompoundDataClassBrick thisAsBrick, CursorPositionDCHolder cursorPositionDCHolder) {
    CompoundDataClassBrick cxcyDCB = (CompoundDataClassBrick) thisAsBrick.getInner("cxcy");
    PrimitiveDataClassBrick cxDCB = (PrimitiveDataClassBrick) cxcyDCB.getInner("cx");
    PrimitiveDataClassBrick cyDCB = (PrimitiveDataClassBrick) cxcyDCB.getInner("cy");
    int cx = (int) cxDCB.getDFB().getVal();
    int cy = (int) cyDCB.getDFB().getVal();
    int a = 0;
    if(cy > 0) {
      a += newlineIndices.get(cy - 1) + 1;
    }
    a += cx;
    PrimitiveDataClassBrick aDCB = cursorPositionDCHolder.wholeNumberDC.makeBrick(a, thisAsBrick, cursorPositionDCHolder);
    return Result.make(aDCB, null);
  }

}
