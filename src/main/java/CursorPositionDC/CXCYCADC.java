package CursorPositionDC;

import DataClass.*;
import DataClass.Result;

import java.util.ArrayList;
import java.util.HashMap;

public class CXCYCADC extends CompoundDataClass {
  public CXCYCADC(int minimumRequiredSetValues) {
    super(minimumRequiredSetValues);
  }

  /**
   * if cx, cy, & ca are set, extract them and newlineIndices from their respective bricks via cxcycaDCB
   * else, return false because no conflicts can exist if any of those values is unset
   * return whether cx, cy, and ca conflict by calling cxcycaConflict(newlineIndices, cx, cy, ca)
   * @param cxcycaDCB the source of all internal brick data (for cx, cy, and ca)
   * @param targetName the name of the brick we want to add targetVal to
   * @param targetVal the value attempting to be set in this brick
   * @return whether targetVal can be set without creating conflicts
   */
  @Override
  public boolean conflictsCheck(CompoundDataClassBrick cxcycaDCB, String targetName, Object targetVal) {
    PrimitiveDataClassBrick niDCB = (PrimitiveDataClassBrick) cxcycaDCB.getOuter().getInner("ni");
    ArrayList<Integer> newlineIndices = (ArrayList<Integer>) niDCB.get().getVal();
    CompoundDataClassBrick cxcyDCB = (CompoundDataClassBrick) cxcycaDCB.getInner("cxcy");
    PrimitiveDataClassBrick cxDCB = (PrimitiveDataClassBrick) cxcyDCB.getInner("cx");
    PrimitiveDataClassBrick cyDCB = (PrimitiveDataClassBrick) cxcyDCB.getInner("cy");
    PrimitiveDataClassBrick caDCB = (PrimitiveDataClassBrick) cxcycaDCB.getInner("ca");
    if((cxDCB.isComplete() || targetName.equals("cx")) && (cyDCB.isComplete() || targetName.equals("cy")) && (caDCB.isComplete() || targetName.equals("ca"))) {
      int cx, cy, ca;
      switch(targetName) {
        case "cx":
          cx = (int)targetVal;
          cy = (int)cyDCB.get().getVal();
          ca = (int)caDCB.get().getVal();
          break;
        case "cy":
          cx = (int)cxDCB.get().getVal();
          cy = (int)targetVal;
          ca = (int)caDCB.get().getVal();
          break;
        case "ca":
          cx = (int)cxDCB.get().getVal();
          cy = (int)cyDCB.get().getVal();
          ca = (int)targetVal;
          break;
        default:
          System.out.println("name not recognized");
          cx = (int)cxDCB.get().getVal();
          cy = (int)cyDCB.get().getVal();
          ca = (int)caDCB.get().getVal();
      }
      return cxcycaConflict(newlineIndices, cx, cy, ca);
    } else {
      return false;
    }
  }

  /**
   * create vars for checking if caLinesUpWithCX & caLinesUpWithCY
   * if cy == 0, caLinesUpWithCY no matter what, and caLinesUpWithCX if cx == ca
   * else,
   * caLinesUpWithCX = cx == the difference between ca, and ((the ni element at cy - 1) + 1)
   * if cy < length of newlineIndices - 1, caLinesUpWithCY = ca > ni.get(cy - 1) && ca < ni.get(cy)
   * else, caLinesUpWithCY = ca > ni.get(cy - 1)
   * return if both caLinesUpWithCX AND caLinesUpWithCY is not true
   * @param newlineIndices
   * @param cx
   * @param cy
   * @param ca
   * @return
   */
  private boolean cxcycaConflict(ArrayList<Integer> newlineIndices, int cx, int cy, int ca) {
    boolean caLinesUpWithCX;
    boolean caLinesUpWithCY;
    if(cy == 0) {
      caLinesUpWithCY = true;
      caLinesUpWithCX = cx == ca;
    } else {
      caLinesUpWithCX = cx == ca - (newlineIndices.get(cy - 1) + 1);
      if(cy < newlineIndices.size() - 1) {
        caLinesUpWithCY = ca > newlineIndices.get(cy - 1) && ca < newlineIndices.get(cy);
      } else {
        caLinesUpWithCY = ca > newlineIndices.get(cy - 1);
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
      if(cxcycaDCB.isComplete()) {
        if (name.equals("ca")) {
          r = calculateCA(newlineIndices, cxcycaDCB);
        } else {
          r = calculateCXCY(newlineIndices, cxcycaDCB);
          CompoundDataClassBrick cxcyDCB = (CompoundDataClassBrick) r.getVal();
          if (name.equals("cx")) {
            r.putVal(cxcyDCB.getInner("cx"));
          } else if (name.equals("cy")) {
            r.putVal(cxcyDCB.getInner("cy"));
          }
        }
      } else {
        r = Result.make(null, "insufficient values set");
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
    CompoundDataClassBrick cxcyDCB = (CompoundDataClassBrick) thisAsBrick.getInner("cxcy");
    PrimitiveDataClassBrick cxDCB = (PrimitiveDataClassBrick) cxcyDCB.getInner("cx");
    PrimitiveDataClassBrick cyDCB = (PrimitiveDataClassBrick) cxcyDCB.getInner("cy");
    cxDCB.putSafe(cx);
    cyDCB.putSafe(cy);
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
    PrimitiveDataClassBrick caDCB = (PrimitiveDataClassBrick) thisAsBrick.getInner("ca");
    caDCB.putSafe(ca);
    return Result.make(caDCB, null);
  }

}
