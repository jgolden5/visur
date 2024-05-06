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
  public Result<DataClassBrick> calcInternal(String name, DataClassBrick dcb, DCHolder dcHolder) {
    Result<DataClassBrick> r;
    CursorPositionDCHolder cursorPositionDCHolder = (CursorPositionDCHolder) dcHolder;
    CompoundDataClassBrick thisAsDCB;
    if(dcb.name.equals("ca") || dcb.name.equals("cxcy")) {
      thisAsDCB = dcb.getOuter().getOuter();
    } else {
      thisAsDCB = dcb.getOuter();
    }
    PrimitiveDataClassBrick niDCB = (PrimitiveDataClassBrick) thisAsDCB.getInner("ni");
    ArrayList<Integer> newlineIndices = (ArrayList<Integer>) niDCB.getDFB().getVal();
    if(dcb.name.equals("ca")) {
      r = calculateCXCY(newlineIndices, thisAsDCB, cursorPositionDCHolder);
    } else {
      r = calculateCA(newlineIndices, thisAsDCB, cursorPositionDCHolder);
    }
    return r;
  }

  private Result<DataClassBrick> calculateCXCY(ArrayList<Integer> newlineIndices, CompoundDataClassBrick cxcyaDCB, CursorPositionDCHolder cursorPositionDCHolder) {
    PrimitiveDataClassBrick caDCB = (PrimitiveDataClassBrick) cxcyaDCB.getInner("ca");
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
    CompoundDataClassBrick cxcyDCB = cursorPositionDCHolder.wholePairDC.makeBrick(cursorPositionDCHolder, cxcyaDCB);
    PrimitiveDataClassBrick cxDCB = cursorPositionDCHolder.wholeNumberDC.makeBrick(cx, cxcyaDCB, cursorPositionDCHolder);
    PrimitiveDataClassBrick cyDCB = cursorPositionDCHolder.wholeNumberDC.makeBrick(cy, cxcyaDCB, cursorPositionDCHolder);
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
