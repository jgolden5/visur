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

  @Override
  public boolean conflictsCheck(CompoundDataClassBrick cxcycaDCB, String targetName, Object targetVal) {
    PrimitiveDataClassBrick niDCB = (PrimitiveDataClassBrick) cxcycaDCB.getOuter().getInner("ni");
    ArrayList<Integer> newlineIndices = (ArrayList<Integer>) niDCB.get().getVal();
    CompoundDataClassBrick longCXCYDCB = (CompoundDataClassBrick) cxcycaDCB.getInner("longCXCY");
    CompoundDataClassBrick shortCXCYDCB = (CompoundDataClassBrick) cxcycaDCB.getInner("shortCXCY");
    PrimitiveDataClassBrick caDCB = (PrimitiveDataClassBrick) cxcycaDCB.getInner("ca");
    PrimitiveDataClassBrick cwDCB = (PrimitiveDataClassBrick) shortCXCYDCB.getInner("cw");
    int canvasWidth = (int)cwDCB.getVal();
    if(moreThanOneValueWillBeSet(targetName, longCXCYDCB, shortCXCYDCB, caDCB)) {
      int[] cxcyca = getCXCYCA(targetName, longCXCYDCB, shortCXCYDCB, caDCB);
      return cxcycaConflict(newlineIndices, canvasWidth, cxcyca[0], cxcyca[1], cxcyca[2], cxcyca[3], cxcyca[4]);
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

  private boolean cxcycaConflict(ArrayList<Integer> newlineIndices, int canvasWidth, int longCX, int longCY, int shortCX, int shortCY, int ca) {
    boolean longCXCYConflictsWithShortCXCY = false;
    boolean longCXCYConflictsWithCA = false;
    boolean shortCXCYConflictsWithCA = false;
    if(gtNegOne(longCX, longCY, shortCX, shortCY) && longCXCYConflictsWithShortCXCY(newlineIndices, canvasWidth, longCX, longCY, shortCX, shortCY)) {
      longCXCYConflictsWithShortCXCY = true;
    } else if(gtNegOne(longCX, longCY, ca) && longCXCYConflictsWithCA(newlineIndices, longCX, longCY, ca)) {
      longCXCYConflictsWithCA = true;
    } else if(gtNegOne(shortCX, shortCY, ca) && shortCXCYConflictsWithCA(newlineIndices, canvasWidth, shortCX, shortCY, ca)) {
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

  private boolean longCXCYConflictsWithShortCXCY(ArrayList<Integer> newlineIndices, int canvasWidth, int longCX, int longCY, int shortCX, int shortCY) {
    boolean cxsConflict = longCX % canvasWidth != shortCX;
    boolean cysConflict;
    int testLongCY = 0;
    int testShortCY = 0;
    int absIndex = 0;
    while(testShortCY < shortCY) {
      absIndex += Math.min(canvasWidth, newlineIndices.get(testShortCY));
      if(absIndex + canvasWidth > newlineIndices.get(testLongCY)) {
        absIndex = newlineIndices.get(testLongCY);
        testLongCY++;
      } else {
        absIndex += canvasWidth;
      }
      testShortCY++;
    }
    cysConflict = testShortCY != shortCY && testLongCY != longCY;
    return (!cxsConflict && !cysConflict);
  }

  private boolean longCXCYConflictsWithCA(ArrayList<Integer> newlineIndices, int longCX, int longCY, int ca) {
    boolean caLinesUpWithCX = false;
    boolean caLinesUpWithCY = false;
    if(longCY == 0) {
      caLinesUpWithCY = true;
      caLinesUpWithCX = longCX == ca;
    } else if(longCY - 1 < newlineIndices.size()) {
      caLinesUpWithCX = longCX == ca - (newlineIndices.get(longCY - 1) + 1);
      if (longCY < newlineIndices.size() - 1) {
        caLinesUpWithCY = ca > newlineIndices.get(longCY - 1) && ca <= newlineIndices.get(longCY);
      } else {
        caLinesUpWithCY = ca > newlineIndices.get(longCY - 1);
      }
    }
    return !(caLinesUpWithCX && caLinesUpWithCY);
  }

  private boolean shortCXCYConflictsWithCA(ArrayList<Integer> newlineIndices, int canvasWidth, int shortCX, int shortCY, int ca) {
    boolean caLinesUpWithCX = ca % canvasWidth == shortCX;
    boolean caLinesUpWithCY;
    int i = 0;
    int absIndex = 0;
    if(newlineIndices.size() > 0) {
      while (i < newlineIndices.size()) {
        absIndex += Math.min(canvasWidth, newlineIndices.get(i));
        if (absIndex + canvasWidth > newlineIndices.get(i)) {
          absIndex = newlineIndices.get(i);
          i++;
        } else {
          absIndex += canvasWidth;
        }
      }
    }
    absIndex += shortCX;
    caLinesUpWithCY = absIndex == ca;

    return !(caLinesUpWithCX && caLinesUpWithCY);
  }

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
    Result<DataClassBrick> r;
    CompoundDataClassBrick longCXCYDCB = (CompoundDataClassBrick) getInner("longCXCY"); //assumes longCXCYToCA is easier than shortToCA
    if(longCXCYDCB.isComplete()) {
      r = calcCAFromLongCXCY(newlineIndices, thisAsBrick);
    } else {
      r = calcCAFromShortCXCY(newlineIndices, thisAsBrick);
    }
    return r;
  }

  private Result<DataClassBrick> calcLongCXCY(String name, ArrayList<Integer> newlineIndices, CompoundDataClassBrick cxcycaDCB) {
    Result<DataClassBrick> r;
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
    int i = 0;
    for(int testShortCY = 0; testShortCY < shortCY; testShortCY++) {
      if(newlineIndices.size() > 1) {
        if (ca + canvasWidth < newlineIndices.get(i)) {
          ca += canvasWidth;
        } else {
          ca = newlineIndices.get(i);
          i++;
        }
      }
    }
    ca += shortCX;
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
    PrimitiveDataClassBrick cwDCB = (PrimitiveDataClassBrick) shortCXCYDCB.getInner("cw");
    int canvasWidth = (int)cwDCB.getVal();
    int shortCX = (int)shortCXDCB.getVal();
    int shortCY = (int)shortCYDCB.getVal();
    int longCX = shortCX;
    int longCY = 0;
    int absolute = 0;
    for(int testShortCY = 0; testShortCY < shortCY; testShortCY++) {
      if(newlineIndices.size() > 0 && absolute + canvasWidth < newlineIndices.get(longCY)) {
        absolute += canvasWidth;
        longCX = Math.min(longCX + canvasWidth, newlineIndices.get(longCY));
      } else {
        absolute = newlineIndices.get(longCY);
        longCY++;
        longCX = shortCX;
      }
    }
    CompoundDataClassBrick longCXCYDCB = (CompoundDataClassBrick) cxcycaDCB.getInner("longCXCY");
    PrimitiveDataClassBrick longCXDCB = (PrimitiveDataClassBrick) longCXCYDCB.getInner("longCX");
    PrimitiveDataClassBrick longCYDCB = (PrimitiveDataClassBrick) longCXCYDCB.getInner("longCY");
    longCXDCB.putSafe(longCX);
    longCYDCB.putSafe(longCY);
    return Result.make(longCXCYDCB, null);
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
