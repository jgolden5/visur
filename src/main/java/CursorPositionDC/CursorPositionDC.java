package CursorPositionDC;

import DataClass.*;
import com.ple.visur.Result;

import java.util.ArrayList;
import java.util.HashMap;

public class CursorPositionDC extends CompoundDataClass {
  public CursorPositionDC(int minimumRequiredSetValues) {
    super(minimumRequiredSetValues);
  }

  @Override
  public CompoundDataClassBrick makeBrick(DCHolder dcHolder, CompoundDataClassBrick outer) {
    return CompoundDataClassBrick.make(outer, this, new HashMap<>());
  }

  @Override
  public Result<DataClassBrick> calculateInnerBrick(String name, CompoundDataClassBrick thisAsBrick, CursorPositionDCHolder cursorPositionDCHolder) {
    Result<DataClassBrick> res = Result.make(null, null);
    PrimitiveDataClassBrick newlineIndicesDCB = (PrimitiveDataClassBrick) thisAsBrick.getInner("ni");
    ArrayList<Integer> newlineIndices = (ArrayList<Integer>) newlineIndicesDCB.getDFB().getVal();
    if(name.equals("cxcy")) {
      res = calculateCXCY(newlineIndices, thisAsBrick, cursorPositionDCHolder);
    } else if(name.equals("a")) {
      res = calculateA(newlineIndices, thisAsBrick, cursorPositionDCHolder);
    } else if(name.equals("ni")) {
      res.putError("newline indices cannot be calculated when unset");
    } else {
      res.putError("inner brick not recognized");
    }
    return res;
  }

  private Result<DataClassBrick> calculateCXCY(ArrayList<Integer> newlineIndices, CompoundDataClassBrick thisAsBrick, CursorPositionDCHolder cursorPositionDCHolder) {
    PrimitiveDataClassBrick aDCB = (PrimitiveDataClassBrick) thisAsBrick.getInner("a");
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
      cx = a - newlineIndices.get(cy - 1);
    } else {
      cx = a;
    }
    CompoundDataClassBrick cxcyDCB = cursorPositionDCHolder.wholePairDC.makeBrick(cursorPositionDCHolder, thisAsBrick);
    PrimitiveDataClassBrick cxDCB = cursorPositionDCHolder.wholeNumberDC.makeBrick(cx, cxcyDCB, cursorPositionDCHolder);
    PrimitiveDataClassBrick cyDCB = cursorPositionDCHolder.wholeNumberDC.makeBrick(cy, cxcyDCB, cursorPositionDCHolder);
    cxcyDCB.putInner("cx", cxDCB);
    cxcyDCB.putInner("cy", cyDCB);
    return Result.make(cxcyDCB, null);
  }

  private Result<DataClassBrick> calculateA(ArrayList<Integer> newlineIndices, CompoundDataClassBrick thisAsBrick, CursorPositionDCHolder cursorPositionDCHolder) {
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
