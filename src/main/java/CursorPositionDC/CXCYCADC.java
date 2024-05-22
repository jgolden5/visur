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
  public boolean conflictsCheck(CompoundDataClassBrick cxcycaDCB, String targetName, Object targetVal) {
    PrimitiveDataClassBrick niDCB = (PrimitiveDataClassBrick) cxcycaDCB.getOuter().getInner("ni");
    ArrayList<Integer> newlineIndices = (ArrayList<Integer>) niDCB.get().getVal();
    boolean cxcyAndCAAreComplete = checkCXCYAndCAAreComplete(cxcycaDCB, targetName);
    if(cxcyAndCAAreComplete) {
      int[] cxcyca = cxcycaInit(cxcycaDCB, targetName, targetVal);
      int cx = cxcyca[0];
      int cy = cxcyca[1];
      int ca = cxcyca[2];
      return checkCALinesUpWithCXCY(newlineIndices, cx, cy, ca);
    } else {
      return false;
    }
  }

  private boolean checkCXCYAndCAAreComplete(CompoundDataClassBrick cxcycaDCB, String targetName) {
    boolean cxcyDCBIsComplete;
    boolean caDCBIsComplete;
    CompoundDataClassBrick cxcyDCB = (CompoundDataClassBrick) cxcycaDCB.getInner("cxcy");
    PrimitiveDataClassBrick caDCB = (PrimitiveDataClassBrick) cxcycaDCB.getInner("ca");
    if(targetName.equals("ca")) {
      cxcyDCBIsComplete = cxcyDCB.isComplete();
      caDCBIsComplete = true;
    } else {
      cxcyDCBIsComplete = cxcyDCB.isComplete(targetName);
      caDCBIsComplete = caDCB.isComplete();
    }
    return cxcyDCBIsComplete && caDCBIsComplete;
  }

  private int[] cxcycaInit(CompoundDataClassBrick cxcycaDCB, String targetName, Object targetVal) {
    int[] cxcycaInts = new int[3];
    CompoundDataClassBrick cxcyDCB = (CompoundDataClassBrick) cxcycaDCB.getInner("cxcy");
    PrimitiveDataClassBrick caDCB = (PrimitiveDataClassBrick) cxcycaDCB.getInner("ca");
    PrimitiveDataClassBrick cxDCB = (PrimitiveDataClassBrick)cxcyDCB.getInner("cx");
    PrimitiveDataClassBrick cyDCB = (PrimitiveDataClassBrick)cxcyDCB.getInner("cy");
    int cx = (int) cxDCB.get().getVal();
    int cy = (int) cyDCB.get().getVal();
    int ca = (int) caDCB.get().getVal();
    if (targetName.equals("ca")) {
      ca = (int) targetVal;
    } else {
      if (targetName.equals("cx")) {
        cx = (int) targetVal;
      } else {
        cy = (int) targetVal;
      }
    }
    cxcycaInts[0] = cx;
    cxcycaInts[1] = cy;
    cxcycaInts[2] = ca;
    return cxcycaInts;
  }

  private boolean checkCALinesUpWithCXCY(ArrayList<Integer> newlineIndices, int cx, int cy, int ca) {
    boolean caLinesUpWithCX;
    boolean caLinesUpWithCY;
    if (cy < 1) {
      caLinesUpWithCX = cx == ca;
      caLinesUpWithCY = true;
    } else {
      caLinesUpWithCX = cx == ca - newlineIndices.get(cy - 1);
      if (cy < newlineIndices.size() - 1) {
        caLinesUpWithCY = ca > newlineIndices.get(cy - 1);
      } else {
        caLinesUpWithCY = ca > newlineIndices.get(cy - 1) && ca < newlineIndices.get(cy);
      }
    }
    return !(caLinesUpWithCX && caLinesUpWithCY);
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
    CompoundDataClassBrick cxcycaDCB = CompoundDataClassBrick.make(name, outer, this, cxcycaDCBInners);
    PrimitiveDataClass wholeNumberDC = (PrimitiveDataClass) getInner("wholeNumber");
    CompoundDataClass wholePairDC = (CompoundDataClass) getInner("cxcy");
    PrimitiveDataClassBrick caDCB = (PrimitiveDataClassBrick) wholeNumberDC.makeBrick("ca", cxcycaDCB);
    cxcycaDCBInners.put("ca", caDCB);
    CompoundDataClassBrick cxcyDCB = (CompoundDataClassBrick) wholePairDC.makeBrick("cxcy", cxcycaDCB);
    cxcycaDCBInners.put("cxcy", cxcyDCB);
    return cxcycaDCB.initInners(cxcycaDCBInners);
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
//    cxDCB.putSafe(cxDCB); //figure out another way to set these without calling the now obsolete cdcb.putInner
//    cyDCB.putSafe(cyDCB);
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
