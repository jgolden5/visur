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
  public DataClassBrick makeBrick() {
    return null;
  }

  @Override
  public DataClassBrick makeBrick(String name, CompoundDataClassBrick outer) {
    HashMap<String, DataClassBrick> cxcycaDCBInners = new HashMap<>();
    CompoundDataClassBrick cxcycaDCB = CompoundDataClassBrick.make(name, outer, this, cxcycaDCBInners);

    CompoundDataClass longCXCYDC = (CompoundDataClass) getInner("longCXCY");
    PrimitiveDataClassBrick longCXCYDCB = (PrimitiveDataClassBrick) longCXCYDC.makeBrick("longCXCY", cxcycaDCB);

    CompoundDataClass shortCXCYDC = (CompoundDataClass) getInner("shortCXCY");
    PrimitiveDataClassBrick shortCXCYDCB = (PrimitiveDataClassBrick) shortCXCYDC.makeBrick("shortCXCY", cxcycaDCB);

    PrimitiveDataClass wholeNumberDC = (PrimitiveDataClass) getInner("wholeNumber");
    PrimitiveDataClassBrick caDCB = (PrimitiveDataClassBrick) wholeNumberDC.makeBrick("ca", cxcycaDCB);

    cxcycaDCBInners.put("longCXCY", longCXCYDCB);
    cxcycaDCBInners.put("shortCXCY", shortCXCYDCB);
    cxcycaDCBInners.put("ca", caDCB);

    return cxcycaDCB.initInners(cxcycaDCBInners);
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
    CompoundDataClassBrick longCXCYDCB = (CompoundDataClassBrick) cxcycaDCB.getInner("longCXCY");
    CompoundDataClassBrick shortCXCYDCB = (CompoundDataClassBrick) cxcycaDCB.getInner("shortCXCY");
    PrimitiveDataClassBrick caDCB = (PrimitiveDataClassBrick) cxcycaDCB.getInner("ca");
    if(moreThanOneValueWillBeSet(targetName, longCXCYDCB, shortCXCYDCB, caDCB)) {
      int[] cxcyca = getCXCYCA(targetName, longCXCYDCB, shortCXCYDCB, caDCB);
      return cxcycaConflict(newlineIndices, cxcyca[0], cxcyca[1], cxcyca[2], cxcyca[3], cxcyca[4]);
    } else {
      return false;
    }
  }

  private boolean moreThanOneValueWillBeSet(String targetName, CompoundDataClassBrick longCXCYDCB, CompoundDataClassBrick shortCXCYDCB, PrimitiveDataClassBrick caDCB) {
    boolean longCXCYIsOrWillBeSet = false;
    boolean shortCXCYIsOrWillBeSet = false;
    boolean caIsOrWillBeSet = false;
    switch(targetName) {
      case "longCXCY":
        longCXCYIsOrWillBeSet = true;
        shortCXCYIsOrWillBeSet = shortCXCYDCB.isComplete();
        caIsOrWillBeSet = caDCB.isComplete();
        break;
      case "shortCXCY":
        longCXCYIsOrWillBeSet = longCXCYDCB.isComplete();
        shortCXCYIsOrWillBeSet = true;
        caIsOrWillBeSet = caDCB.isComplete();
        break;
      case "ca":
        longCXCYIsOrWillBeSet = longCXCYDCB.isComplete();
        shortCXCYIsOrWillBeSet = shortCXCYDCB.isComplete();
        caIsOrWillBeSet = true;
        break;
      default:
        System.out.println("target name not recognized for checking if more than one value will be set in cxcycaDCB");
    }
    return longCXCYIsOrWillBeSet && shortCXCYIsOrWillBeSet || shortCXCYIsOrWillBeSet && caIsOrWillBeSet || longCXCYIsOrWillBeSet && caIsOrWillBeSet;
  }

  private int[] getCXCYCA(String targetName, CompoundDataClassBrick longCXCYDCB, CompoundDataClassBrick shortCXCYDCB, PrimitiveDataClassBrick caDCB) {
    int[] cxcyca = new int[5];
    int longCX = -1;
    int longCY = -1;
    int shortCX = -1;
    int shortCY = -1;
    int ca = -1;
    if(longCXCYDCB.isComplete()) {
      PrimitiveDataClassBrick longCXDCB = (PrimitiveDataClassBrick) longCXCYDCB.getInner("cx");
      longCX = (int)longCXDCB.getVal();
      PrimitiveDataClassBrick longCYDCB = (PrimitiveDataClassBrick) longCXCYDCB.getInner("cy");
      longCY = (int)longCYDCB.getVal();
    }
    if(shortCXCYDCB.isComplete()) {
      PrimitiveDataClassBrick shortCXDCB = (PrimitiveDataClassBrick) shortCXCYDCB.getInner("cx");
      shortCX = (int)shortCXDCB.getVal();
      PrimitiveDataClassBrick shortCYDCB = (PrimitiveDataClassBrick) shortCXCYDCB.getInner("cy");
      shortCY = (int)shortCYDCB.getVal();
    }
    if(caDCB.isComplete()) {
      ca = (int)caDCB.getVal();
    }
    cxcyca[0] = longCX;
    cxcyca[1] = longCY;
    cxcyca[2] = shortCX;
    cxcyca[3] = shortCY;
    cxcyca[4] = ca;
    return cxcyca;
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
  private boolean cxcycaConflict(ArrayList<Integer> newlineIndices, int longCX, int longCY, int shortCX, int shortCY, int ca) {
    boolean longCXCYConflictsWithShortCXCY = false;
    boolean longCXCYConflictsWithCA = false;
    boolean shortCXCYConflictsWithCA = false;
    if(gtNegOne(longCX, longCY, shortCX, shortCY) && _) {
      longCXCYConflictsWithShortCXCY = true;
    } else if(gtNegOne(longCX, longCY, ca) && _) {
      longCXCYConflictsWithCA = true;
    } else if(gtNegOne(shortCX, shortCY, ca) && _) {
      shortCXCYConflictsWithCA = true;
    }
    return longCXCYConflictsWithShortCXCY || longCXCYConflictsWithCA || shortCXCYConflictsWithCA;
  }

  private boolean gtNegOne(int... nums) {
    for(int n : nums) {
      if(n <= -1) return false;
    }
    return true;
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
    String possibleNames = "longCXCY" + "shortCXCY" + "ca";
    if(!possibleNames.contains(name)) {
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
        r = calcCA(newlineIndices, cxcycaDCB);
      } else if(name.contains("long")) {
        r = calcLongCXCY(name, newlineIndices, cxcycaDCB);
      } else if(name.contains("short")) {
        r = calcShortCXCY(name, newlineIndices, cxcycaDCB);
      }
    }
    return r;
  }

  private Result<DataClassBrick> calcCA(ArrayList<Integer> newlineIndices, CompoundDataClassBrick thisAsBrick) {
    Result r = Result.make();
    CompoundDataClassBrick longCXCYDCB = (CompoundDataClassBrick) getInner("longCXCY"); //assuming longCXCYToCA is easier than shortToCA
    if(longCXCYDCB.isComplete()) {
      r = calcCAFromLongCXCY(newlineIndices, cxcycaDCB);
    } else {
      r = calcCAFromShortCXCY(newlineIndices, cxcycaDCB);
    }
    return r;
  }

  private Result<DataClassBrick> calcLongCXCY(String name, ArrayList<Integer> newlineIndices, CompoundDataClassBrick cxcycaDCB) {
    Result<DataClassBrick> r = Result.make();
    CompoundDataClassBrick shortCXCYDCB = (CompoundDataClassBrick) getInner("shortCXCY");
    if(shortCXCYDCB.isComplete()) {
      r = calcLongCXCYFromShortCXCY(newlineIndices, cxcycaDCB);
    } else {
      r = calcLongCXCYFromCA(newlineIndices, cxcycaDCB);
    }
    CompoundDataClassBrick cxcyDCB = (CompoundDataClassBrick) r.getVal();
    if(name.equals("longCX")) {
      r.putVal(cxcyDCB.getInner("longCX"));
    } else if(name.equals("longCY")) {
      r.putVal(cxcyDCB.getInner("longCY"));
    }
    return r;
  }

  private Result<DataClassBrick> calcShortCXCY(String name, ArrayList<Integer> newlineIndices, CompoundDataClassBrick cxcycaDCB) {
    Result<DataClassBrick> r = Result.make();
    CompoundDataClassBrick longCXCYDCB = (CompoundDataClassBrick) getInner("longCXCY");
    if(longCXCYDCB.isComplete()) {
      r = calcShortCXCYFromLongCXCY(newlineIndices, cxcycaDCB);
    } else {
      r = calcShortCXCYFromCA(newlineIndices, cxcycaDCB);
    }
    CompoundDataClassBrick cxcyDCB = (CompoundDataClassBrick) r.getVal();
    if(name.equals("shortCX")) {
      r.putVal(cxcyDCB.getInner("shortCX"));
    } else if(name.equals("shortCY")) {
      r.putVal(cxcyDCB.getInner("shortCY"));
    }
    return r;
  }

  private Result<DataClassBrick> calcCAFromLongCXCY(ArrayList<Integer> newlineIndices, CompoundDataClassBrick thisAsBrick) {
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

  private Result<DataClassBrick> calcCAFromShortCXCY(ArrayList<Integer> newlineIndices, CompoundDataClassBrick cxcycaDCB) {
    CompoundDataClassBrick shortCXCY = (CompoundDataClassBrick) cxcycaDCB.getInner("shortCXCY");
    PrimitiveDataClassBrick shortCXDCB = (PrimitiveDataClassBrick) shortCXCY.getInner("shortCX");
    PrimitiveDataClassBrick shortCYDCB = (PrimitiveDataClassBrick) shortCXCY.getInner("shortCY");
    PrimitiveDataClassBrick cwDCB = (PrimitiveDataClassBrick) shortCXCY.getInner("cw");
    int shortCX = (int)shortCXDCB.getVal();
    int shortCY = (int)shortCYDCB.getVal();
    int canvasWidth = (int)cwDCB.getVal();
    int ca = 0;
    int newlineIndex = 0;
    for(int i = 0; i < shortCY; i += canvasWidth) {
      if(i < canvasWidth) {
        //this one is tricksy, precious!
      }
    }
    PrimitiveDataClassBrick caDCB = (PrimitiveDataClassBrick) cxcycaDCB.getInner("ca");
    caDCB.putSafe(ca);
    return Result.make(caDCB, null);
  }

  private Result<DataClassBrick> calcLongCXCYFromCA(ArrayList<Integer> newlineIndices, CompoundDataClassBrick thisAsBrick) {
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

  private Result<DataClassBrick> calcLongCXCYFromShortCXCY(ArrayList<Integer> newlineIndices, CompoundDataClassBrick cxcycaDCB) { //harder than calculating long from ca
    CompoundDataClassBrick shortCXCYDCB = (CompoundDataClassBrick) cxcycaDCB.getInner("shortCXCY");
    PrimitiveDataClassBrick shortCXDCB = (PrimitiveDataClassBrick) shortCXCYDCB.getInner("shortCX");
    PrimitiveDataClassBrick shortCYDCB = (PrimitiveDataClassBrick) shortCXCYDCB.getInner("shortCY");
    int shortCX = (int)shortCXDCB.getVal();
    int shortCY = (int)shortCYDCB.getVal();
    int longCX;
    int longCY = ;
    CompoundDataClassBrick longCXCYDCB = (CompoundDataClassBrick) cxcycaDCB.getInner("longCXCY");
    PrimitiveDataClassBrick longCXDCB = (PrimitiveDataClassBrick) longCXCYDCB.getInner("longCX");
    PrimitiveDataClassBrick longCYDCB = (PrimitiveDataClassBrick) longCXCYDCB.getInner("longCY");
    longCXDCB.putSafe(longCX);
    longCYDCB.putSafe(longCY);
  }

  private Result<DataClassBrick> calcShortCXCYFromCA(ArrayList<Integer> newlineIndices, CompoundDataClassBrick cxcycaDCB) {
    CompoundDataClassBrick shortCXCYDCB = (CompoundDataClassBrick) cxcycaDCB.getInner("shortCXCY");
    PrimitiveDataClassBrick caDCB = (PrimitiveDataClassBrick) cxcycaDCB.getInner("ca");
    PrimitiveDataClassBrick cwDCB = (PrimitiveDataClassBrick) shortCXCYDCB.getInner("cw");
    int canvasWidth = (int)cwDCB.getVal();
    int ca = (int)caDCB.getVal();
    int shortCX;
    int shortCY = 0;
    int i;
    for(i = 0; i < newlineIndices.size(); i++) {
      if(newlineIndices.get(i) > ca) {
        if(i > 0) {
          shortCY += newlineIndices.get(i - 1) / canvasWidth;
          shortCY += Math.ceil((ca - newlineIndices.get(i - 1)) / canvasWidth);
        } else {
          shortCY += Math.ceil(ca / canvasWidth);
        }
        break;
      }
    }
    shortCX = (newlineIndices.get(i) - ca) % canvasWidth;
    PrimitiveDataClassBrick shortCXDCB = (PrimitiveDataClassBrick) shortCXCYDCB.getInner("shortCX");
    PrimitiveDataClassBrick shortCYDCB = (PrimitiveDataClassBrick) shortCXCYDCB.getInner("shortCY");
    shortCXDCB.putSafe(shortCX);
    shortCYDCB.putSafe(shortCY);
    return Result.make(shortCXCYDCB, null);
  }

  private Result<DataClassBrick> calcShortCXCYFromLongCXCY(ArrayList<Integer> newlineIndices, CompoundDataClassBrick cxcycaDCB) {
    CompoundDataClassBrick longCXCYDCB = (CompoundDataClassBrick) cxcycaDCB.getInner("longCXCY");
    CompoundDataClassBrick shortCXCYDCB = (CompoundDataClassBrick) cxcycaDCB.getInner("shortCXCY");
    PrimitiveDataClassBrick longCXDCB = (PrimitiveDataClassBrick) longCXCYDCB.getInner("longCX");
    PrimitiveDataClassBrick longCYDCB = (PrimitiveDataClassBrick) longCXCYDCB.getInner("longCY");
    int longCX = (int)longCXDCB.getVal();
    int longCY = (int)longCYDCB.getVal();
    PrimitiveDataClassBrick cwDCB = (PrimitiveDataClassBrick) shortCXCYDCB.getInner("cw");
    int canvasWidth = (int)cwDCB.getVal();
    int shortCX = longCX % canvasWidth;
    int shortCY = 0;
    for(int i = 0; i < longCY; i++) {
      shortCY += newlineIndices.get(i) / canvasWidth;
      if(i + 1 < newlineIndices.size()) {
        shortCY++;
      }
    }
    PrimitiveDataClassBrick shortCXDCB = (PrimitiveDataClassBrick) shortCXCYDCB.getInner("shortCX");
    PrimitiveDataClassBrick shortCYDCB = (PrimitiveDataClassBrick) shortCXCYDCB.getInner("shortCY");
    shortCXDCB.putSafe(shortCX);
    shortCYDCB.putSafe(shortCY);
    return Result.make(shortCXCYDCB, null);
  }

}
