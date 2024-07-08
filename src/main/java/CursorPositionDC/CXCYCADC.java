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
    boolean caLinesUpWithCX = false;
    boolean caLinesUpWithCY = false;
    if(cy == 0) {
      caLinesUpWithCY = true;
      caLinesUpWithCX = cx == ca;
    } else if(cy - 1 < newlineIndices.size()) {
      caLinesUpWithCX = cx == ca - (newlineIndices.get(cy - 1) + 1);
      if(cy < newlineIndices.size() - 1) {
        caLinesUpWithCY = ca > newlineIndices.get(cy - 1) && ca <= newlineIndices.get(cy);
      } else {
        caLinesUpWithCY = ca > newlineIndices.get(cy - 1);
      }
    }
    return !(caLinesUpWithCX && caLinesUpWithCY);
  }

  /**
   * declare empty Result r var
   * "cxcyca".contains(name)...do the following; else r.putError("inner name not recognized")
   * if cxcycaDCB.isComplete...do the following; else r.putError("insufficient values set")
   * extract cursorPositionDCB into var
   * extract newlineIndices from niDCB from cursorPositionDCB
   * if name.equals("ca"), r = calculateCA(newlineIndices, cxcycaDCB)
   * else, r = calculateCXCY(newlinIndices, cxcycaDCB), and do what's on the following lines
   * cxcyDCB = (CDCB)r.getVal
   * if name.equals("cx"), r.putVal(cxcyDCB.getInner("cx"))
   * if name.equals("cy"), r.putVal(cxcyDCB.getInner("cy"))
   * return r
   * @param name of inner being calculated
   * @param cxcycaDCB this as brick
   * @return a result containing the calculated brick if calcInternal succeeded, else val = null & error != null
   */
  @Override
  public Result<DataClassBrick> calcInternal(String name, CompoundDataClassBrick cxcycaDCB) {
    Result r = Result.make();
    if(!"cxcyca".contains(name)) {
      r.putError("inner name not recognized");
    }
    if(!cxcycaDCB.isComplete()) {
      r.putError("insufficient values set");
    }
    if(r.getError() == null) {
      CompoundDataClassBrick cursorPositionDCB = cxcycaDCB.getOuter();
      PrimitiveDataClassBrick niDCB = (PrimitiveDataClassBrick) cursorPositionDCB.getInner("ni");
      ArrayList<Integer> newlineIndices = (ArrayList<Integer>) niDCB.get().getVal();
      if(name.equals("ca")) {
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
    CompoundDataClass cxcyDC = (CompoundDataClass) getInner("cxcy");
    PrimitiveDataClass wholeNumberDC = (PrimitiveDataClass) getInner("wholeNumber");
    PrimitiveDataClassBrick caDCB = (PrimitiveDataClassBrick) wholeNumberDC.makeBrick("ca", cxcycaDCB);
    cxcycaDCBInners.put("ca", caDCB);
    CompoundDataClassBrick cxcyDCB = (CompoundDataClassBrick) cxcyDC.makeBrick("cxcy", cxcycaDCB);
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
