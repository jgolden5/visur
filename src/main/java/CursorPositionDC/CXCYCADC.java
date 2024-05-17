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
  public boolean conflicts(CompoundDataClassBrick cxcycaDCB) {
    CompoundDataClassBrick cursorPositionDCB = cxcycaDCB.getOuter();
    PrimitiveDataClassBrick niDCB = (PrimitiveDataClassBrick) cursorPositionDCB.getInner("ni");
    PrimitiveDataClassBrick caDCB = (PrimitiveDataClassBrick) cxcycaDCB.getInner("ca");
    CompoundDataClassBrick cxcyDCB = (CompoundDataClassBrick) cxcycaDCB.getInner("cxcy");
    PrimitiveDataClassBrick cxDCB = (PrimitiveDataClassBrick) cxcyDCB.getInner("cx");
    PrimitiveDataClassBrick cyDCB = (PrimitiveDataClassBrick) cxcyDCB.getInner("cy");
    if(!(caDCB.isComplete() && cxcyDCB.isComplete())) {
      return false;
    } else {
      int ca = (int) caDCB.get().getVal();
      int cx = (int) cxDCB.get().getVal();
      int cy = (int) cyDCB.get().getVal();
      ArrayList<Integer> newlineIndices = (ArrayList<Integer>) niDCB.get().getVal();
      boolean caLinesUpWithCY;
      boolean caLinesUpWithCX;
      if (cy < 1) {
        caLinesUpWithCX = ca == cx;
        caLinesUpWithCY = ca <= newlineIndices.get(0);
      } else {
        caLinesUpWithCX = cx == ca - (newlineIndices.get(cy - 1) + 1);
        boolean cyIsNotTooHigh = true;
        boolean cyIsNotTooLow = ca > newlineIndices.get(cy - 1);
        if (cy < newlineIndices.size()) {
          cyIsNotTooHigh = ca <= newlineIndices.get(cy);
        }
        caLinesUpWithCY = cyIsNotTooLow && cyIsNotTooHigh;
      }
      return !(caLinesUpWithCX && caLinesUpWithCY);
    }
  }

  @Override
  public Result<DataClassBrick> calcInternal(String name, CompoundDataClassBrick thisAsBrick) {
    Result<DataClassBrick> r = Result.make();
    CompoundDataClassBrick cursorPositionDCB = null;
    CompoundDataClassBrick cxcycaDCB = null;
    if("cxcyca".contains(name)) {
      cxcycaDCB = thisAsBrick;
      cursorPositionDCB = thisAsBrick.getOuter();
    } else {
      r.putError("inner name not recognized");
    }
    if(cursorPositionDCB != null) {
      PrimitiveDataClassBrick niDCB = (PrimitiveDataClassBrick) cursorPositionDCB.getInner("ni");
      ArrayList<Integer> newlineIndices = (ArrayList<Integer>) niDCB.getDFB().getVal();
      if (name.equals("ca")) {
        r = calculateCA(newlineIndices, cxcycaDCB);
      } else {
        r = calculateCXCY(newlineIndices, cxcycaDCB);
        CompoundDataClassBrick cxcyDCB = (CompoundDataClassBrick) r.getVal();
        if(name.equals("cx")) {
          r.putVal(cxcyDCB.getInner("cx"));
        } else if(name.equals("cy")) {
          r.putVal(cxcyDCB.getInner("cy"));
        }
      }
    }
    return r;
  }

  @Override
  public DataClassBrick makeBrick() {
    return null;
  }

  @Override
  public DataClassBrick makeBrick(String name, CompoundDataClassBrick outer) {
    HashMap<String, DataClassBrick> cxcycaDCBInners = new HashMap<>();
    return CompoundDataClassBrick.make(name, outer, this, cxcycaDCBInners);
  }

  private Result<DataClassBrick> calculateCXCY(ArrayList<Integer> newlineIndices, CompoundDataClassBrick thisAsBrick) {
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
    WholePairDC wholePairDC = (WholePairDC)this.getInner("cxcy");
    WholeNumberDC wholeNumberDC = (WholeNumberDC)this.getInner("wholeNumber");
    CompoundDataClassBrick cxcyDCB = wholePairDC.makeBrick("cxcy", thisAsBrick);
    PrimitiveDataClassBrick cxDCB = (PrimitiveDataClassBrick) wholeNumberDC.makeBrick("cx", cxcyDCB);
    PrimitiveDataClassBrick cyDCB = (PrimitiveDataClassBrick)wholeNumberDC.makeBrick("cy", cxcyDCB);
    cxcyDCB.putInner(cxDCB);
    cxcyDCB.putInner(cyDCB);
    return Result.make(cxcyDCB, null);
  }

  private Result<DataClassBrick> calculateCA(ArrayList<Integer> newlineIndices, CompoundDataClassBrick thisAsBrick) {
    CompoundDataClassBrick cxcyDCB = (CompoundDataClassBrick) thisAsBrick.getInner("cxcy");
    PrimitiveDataClassBrick cxDCB = (PrimitiveDataClassBrick) cxcyDCB.getInner("cx");
    PrimitiveDataClassBrick cyDCB = (PrimitiveDataClassBrick) cxcyDCB.getInner("cy");
    int cx = (int) cxDCB.getDFB().getVal();
    int cy = (int) cyDCB.getDFB().getVal();
    int ca = 0;
    if(cy > 0) {
      ca += newlineIndices.get(cy - 1) + 1;
    }
    ca += cx;
    WholeNumberDC wholeNumberDC = (WholeNumberDC)this.getInner("wholeNumber");
    PrimitiveDataClassBrick caDCB = wholeNumberDC.makeBrick("ca", ca, thisAsBrick);
    return Result.make(caDCB, null);
  }

}
