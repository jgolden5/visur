package CursorPositionDC;

import DataClass.*;
import DataClass.Result;

import java.sql.Array;
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
  public Result<DataClassBrick> calcInternal(String name, CompoundDataClassBrick thisAsBrick, DCHolder dcHolder) {
    Result<DataClassBrick> r = Result.make();
    CursorPositionDCHolder cursorPositionDCHolder = (CursorPositionDCHolder) dcHolder;
    CompoundDataClassBrick cursorPositionDCB = null;
    CompoundDataClassBrick cxcyaDCB = null;
    if(name.equals("ca") || name.equals("cxcy")) {
      cxcyaDCB = thisAsBrick;
      cursorPositionDCB = thisAsBrick;
    } else if(name.equals("cx") || name.equals("cy")) {
      cxcyaDCB = thisAsBrick.getOuter();
      cursorPositionDCB = thisAsBrick.getOuter();
    } else {
      r.putError("inner name not recognized");
    }
    if(cursorPositionDCB != null) {
      PrimitiveDataClassBrick niDCB = (PrimitiveDataClassBrick) cursorPositionDCB.getInner("ni");
      ArrayList<Integer> newlineIndices = (ArrayList<Integer>) niDCB.getDFB().getVal();
      if (name.equals("ca")) {
        r = calculateCA(newlineIndices, cxcyaDCB, cursorPositionDCHolder);
      } else {
        r = calculateCXCY(newlineIndices, cxcyaDCB, cursorPositionDCHolder);
      }
    }
    return r;
  }

  private Result<DataClassBrick> calculateCXCY(ArrayList<Integer> newlineIndices, CompoundDataClassBrick thisAsBrick, CursorPositionDCHolder cursorPositionDCHolder) {
    PrimitiveDataClassBrick caDCB = (PrimitiveDataClassBrick) thisAsBrick.getInner("ca");
    int ca = (int)caDCB.getDFB().getVal();
    int cx;
    int cy = 0;
    for(int i = 0; i < newlineIndices.size(); i++) {
      if(ca > newlineIndices.get(i)) {
        cy++;
      } else {
        break;
      }
    }
    if(cy > 0) {
      cx = ca - (newlineIndices.get(cy - 1) + 1);
    } else {
      cx = ca;
    }
    CompoundDataClassBrick cxcyDCB = cursorPositionDCHolder.wholePairDC.makeBrick(cursorPositionDCHolder, thisAsBrick);
    PrimitiveDataClassBrick cxDCB = cursorPositionDCHolder.wholeNumberDC.makeBrick(cx, thisAsBrick, cursorPositionDCHolder);
    PrimitiveDataClassBrick cyDCB = cursorPositionDCHolder.wholeNumberDC.makeBrick(cy, thisAsBrick, cursorPositionDCHolder);
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
