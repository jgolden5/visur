package CursorPositionDC;

import DataClass.*;
import DataClass.Result;

import java.util.ArrayList;
import java.util.HashMap;

public class CoordinatesDC extends CompoundDataClass {
  public CoordinatesDC(int minimumRequiredSetValues) {
    super(minimumRequiredSetValues);
  }

  @Override
  public DataClassBrick makeBrick() {
    return null;
  }

  @Override
  public DataClassBrick makeBrick(String name, CompoundDataClassBrick outer) {
    HashMap<String, DataClassBrick> coordinatesDCBInners = new HashMap<>();
    CompoundDataClassBrick coordinatesDCB = CompoundDataClassBrick.make(name, outer, this, coordinatesDCBInners);

    CompoundDataClass longCXCYDC = (CompoundDataClass) getInner("longCXCY");
    CompoundDataClassBrick longCXCYDCB = (CompoundDataClassBrick) longCXCYDC.makeBrick("longCXCY", coordinatesDCB);

    CompoundDataClass shortCXCYDC = (CompoundDataClass) getInner("shortCXCY");
    CompoundDataClassBrick shortCXCYDCB = (CompoundDataClassBrick) shortCXCYDC.makeBrick("shortCXCY", coordinatesDCB);

    PrimitiveDataClass wholeNumberDC = (PrimitiveDataClass) getInner("wholeNumber");
    PrimitiveDataClassBrick caDCB = (PrimitiveDataClassBrick) wholeNumberDC.makeBrick("ca", coordinatesDCB);

    coordinatesDCBInners.put("longCXCY", longCXCYDCB);
    coordinatesDCBInners.put("shortCXCY", shortCXCYDCB);
    coordinatesDCBInners.put("ca", caDCB);

    return coordinatesDCB.initInners(coordinatesDCBInners);
  }

  @Override
  public ConflictsCheckResult conflictsCheck(CompoundDataClassBrick coordinatesDCB, String targetName, Object targetVal) {
    PrimitiveDataClassBrick niDCB = (PrimitiveDataClassBrick) coordinatesDCB.getOuter().getInner("ni");
    ArrayList<Integer> newlineIndices = (ArrayList<Integer>) niDCB.get().getVal();
    CompoundDataClassBrick longCXCYDCB = (CompoundDataClassBrick) coordinatesDCB.getInner("longCXCY");
    CompoundDataClassBrick shortCXCYDCB = (CompoundDataClassBrick) coordinatesDCB.getInner("shortCXCY");
    PrimitiveDataClassBrick caDCB = (PrimitiveDataClassBrick) coordinatesDCB.getInner("ca");
    ConflictsCheckResult moreThanOneValueWillBeSet = moreThanOneValueWillBeSet(targetName, longCXCYDCB, shortCXCYDCB, caDCB);
    if(moreThanOneValueWillBeSet == ConflictsCheckResult.yes) {
      int[] coordinates = getCoordinates(targetName, targetVal, longCXCYDCB, shortCXCYDCB, caDCB);
      CompoundDataClassBrick cursorPositionDCB = coordinatesDCB.getOuter();
      PrimitiveDataClassBrick cwDCB = (PrimitiveDataClassBrick) cursorPositionDCB.getInner("cw");
      int canvasWidth = (int) cwDCB.getVal();
      return coordinatesConflict(newlineIndices, canvasWidth, coordinates[0], coordinates[1], coordinates[2], coordinates[3], coordinates[4]);
    } else {
      return moreThanOneValueWillBeSet;
    }
  }

  private ConflictsCheckResult moreThanOneValueWillBeSet(String targetName, CompoundDataClassBrick longCXCYDCB, CompoundDataClassBrick shortCXCYDCB, PrimitiveDataClassBrick caDCB) {
    ConflictsCheckResult longCXCYIsOrWillBeSet, shortCXCYIsOrWillBeSet, caIsOrWillBeSet;
    PrimitiveDataClassBrick longCXDCB = (PrimitiveDataClassBrick) longCXCYDCB.getInner("longCX");
    PrimitiveDataClassBrick longCYDCB = (PrimitiveDataClassBrick) longCXCYDCB.getInner("longCY");
    boolean longCXIsOrWillBeSet = longCXDCB.isComplete() || targetName.equals("longCX");
    boolean longCYIsOrWillBeSet = longCYDCB.isComplete() || targetName.equals("longCY");
    if(longCXIsOrWillBeSet && longCYIsOrWillBeSet) {
      longCXCYIsOrWillBeSet = ConflictsCheckResult.yes;
    } else if(longCXIsOrWillBeSet || longCYIsOrWillBeSet) {
      longCXCYIsOrWillBeSet = ConflictsCheckResult.maybe;
    } else {
      longCXCYIsOrWillBeSet = ConflictsCheckResult.no;
    }
    PrimitiveDataClassBrick shortCXDCB = (PrimitiveDataClassBrick) shortCXCYDCB.getInner("shortCX");
    PrimitiveDataClassBrick shortCYDCB = (PrimitiveDataClassBrick) shortCXCYDCB.getInner("shortCY");
    boolean shortCXIsOrWillBeSet = shortCXDCB.isComplete() || targetName.equals("shortCX");
    boolean shortCYIsOrWillBeSet = shortCYDCB.isComplete() || targetName.equals("shortCY");
    if(shortCXIsOrWillBeSet && shortCYIsOrWillBeSet) {
      shortCXCYIsOrWillBeSet = ConflictsCheckResult.yes;
    } else if(shortCXIsOrWillBeSet || shortCYIsOrWillBeSet) {
      shortCXCYIsOrWillBeSet = ConflictsCheckResult.maybe;
    } else {
      shortCXCYIsOrWillBeSet = ConflictsCheckResult.no;
    }
    caIsOrWillBeSet = caDCB.isComplete() || targetName.equals("ca") ? ConflictsCheckResult.yes : ConflictsCheckResult.no;
    return ConflictsCheckResult.getMostCertainResult(longCXCYIsOrWillBeSet, shortCXCYIsOrWillBeSet, caIsOrWillBeSet);
  }

  private int[] getCoordinates(String targetName, Object targetVal, CompoundDataClassBrick longCXCYDCB, CompoundDataClassBrick shortCXCYDCB, PrimitiveDataClassBrick caDCB) {
    int[] coordinates = new int[5];
    int longCX = -1;
    int longCY = -1;
    int shortCX = -1;
    int shortCY = -1;
    int ca = -1;
    PrimitiveDataClassBrick longCXDCB = (PrimitiveDataClassBrick) longCXCYDCB.getInner("longCX");
    PrimitiveDataClassBrick longCYDCB = (PrimitiveDataClassBrick) longCXCYDCB.getInner("longCY");
    if(longCXCYDCB.isComplete()) {
      longCX = (int)longCXDCB.getVal();
      longCY = (int)longCYDCB.getVal();
    } else if(targetName.equals("longCX") && longCYDCB.isComplete()) {
      longCY = (int)longCYDCB.getVal();
    } else if(targetName.equals("longCY") && longCXDCB.isComplete()) {
      longCX = (int)longCXDCB.getVal();
    }
    PrimitiveDataClassBrick shortCXDCB = (PrimitiveDataClassBrick) shortCXCYDCB.getInner("shortCX");
    PrimitiveDataClassBrick shortCYDCB = (PrimitiveDataClassBrick) shortCXCYDCB.getInner("shortCY");
    if(shortCXCYDCB.isComplete()) {
      shortCX = (int)shortCXDCB.getVal();
      shortCY = (int)shortCYDCB.getVal();
    } else if(targetName.equals("shortCX") && shortCYDCB.isComplete()) {
      shortCY = (int)shortCYDCB.getVal();
    } else if(targetName.equals("shortCY") && shortCXDCB.isComplete()) {
      shortCX = (int)shortCXDCB.getVal();
    }
    if(caDCB.isComplete()) {
      ca = (int)caDCB.getVal();
    }
    coordinates[0] = targetName.equals("longCX") ? (int)targetVal : longCX;
    coordinates[1] = targetName.equals("longCY") ? (int) targetVal : longCY;
    coordinates[2] = targetName.equals("shortCX") ? (int) targetVal : shortCX;
    coordinates[3] = targetName.equals("shortCY") ? (int) targetVal : shortCY;
    coordinates[4] = targetName.equals("ca") ? (int) targetVal : ca;
    return coordinates;
  }

  private ConflictsCheckResult coordinatesConflict(ArrayList<Integer> newlineIndices, int canvasWidth, int longCX, int longCY, int shortCX, int shortCY, int ca) {
    ConflictsCheckResult longCXCYConflictsWithShortCXCY = ConflictsCheckResult.no;
    ConflictsCheckResult longCXCYConflictsWithCA = ConflictsCheckResult.no;
    ConflictsCheckResult shortCXCYConflictsWithCA = ConflictsCheckResult.no;
    if (valuesAreSet(longCX, longCY, shortCX, shortCY)) {
      longCXCYConflictsWithShortCXCY = longCXCYConflictsWithShortCXCY(newlineIndices, canvasWidth, longCX, longCY, shortCX, shortCY);
    } else if (valuesAreSet(longCX, longCY, ca)) {
      longCXCYConflictsWithCA = longCXCYConflictsWithCA(newlineIndices, longCX, longCY, ca);
    } else if (valuesAreSet(shortCX, shortCY, ca)) {
      shortCXCYConflictsWithCA = shortCXCYConflictsWithCA(newlineIndices, canvasWidth, shortCX, shortCY, ca);
    }
    return ConflictsCheckResult.getMostCertainResult(longCXCYConflictsWithCA, longCXCYConflictsWithShortCXCY, shortCXCYConflictsWithCA);
  }

  private boolean valuesAreSet(int... nums) {
    for(int n : nums) {
      if(n <= -1) return false;
    }
    return true;
  }

  private ConflictsCheckResult longCXCYConflictsWithShortCXCY(ArrayList<Integer> newlineIndices, int canvasWidth, int longCX, int longCY, int shortCX, int shortCY) {
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
    if(cxsConflict || cysConflict) {
      return ConflictsCheckResult.yes;
    } else {
      return ConflictsCheckResult.no;
    }
  }

  private ConflictsCheckResult longCXCYConflictsWithCA(ArrayList<Integer> newlineIndices, int longCX, int longCY, int ca) {
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
    if(caLinesUpWithCX && caLinesUpWithCY) {
      return ConflictsCheckResult.no;
    } else {
      return ConflictsCheckResult.yes;
    }
  }

  private ConflictsCheckResult shortCXCYConflictsWithCA(ArrayList<Integer> newlineIndices, int canvasWidth, int shortCX, int shortCY, int ca) {
    int absIndex = 0;
    int nextNewlineChar = newlineIndices.size() > 0 ? newlineIndices.get(0) : shortCX;
    int ni = 0;
    int testShortCY;
    for (testShortCY = 0; testShortCY < shortCY; testShortCY++) {
      if (absIndex + canvasWidth < nextNewlineChar) {
        absIndex += canvasWidth;
      } else {
        absIndex = nextNewlineChar + 1;
        if (newlineIndices.size() > ++ni) {
          nextNewlineChar = newlineIndices.get(ni);
        } else {
          nextNewlineChar = shortCX;
        }
      }
    }
    boolean caLinesUpWithShortCXCY = absIndex + shortCX == ca;

    return caLinesUpWithShortCXCY ? ConflictsCheckResult.no : ConflictsCheckResult.yes;
  }

  @Override
  public Result<DataClassBrick> calcInternal(String name, CompoundDataClassBrick coordinatesDCB) {
    Result r = Result.make();
    String possibleNames = "longCXCY" + "shortCXCY" + "ca";
    if(!possibleNames.contains(name)) {
      r.putError("inner name not recognized");
    }
    if(!coordinatesDCB.isComplete()) {
      r.putError("insufficient values set");
    }
    if(r.getError() == null) {
      CompoundDataClassBrick cursorPositionDCB = coordinatesDCB.getOuter();
      PrimitiveDataClassBrick niDCB = (PrimitiveDataClassBrick) cursorPositionDCB.getInner("ni");
      PrimitiveDataClassBrick cwDCB = (PrimitiveDataClassBrick) cursorPositionDCB.getInner("cw");
      ArrayList<Integer> newlineIndices = (ArrayList<Integer>) niDCB.getVal();
      int canvasWidth = (int)cwDCB.getVal();

      if(name.equals("ca")) {
        r = calcCA(newlineIndices, coordinatesDCB);
      } else if(name.contains("long")) {
        r = calcLongCXCY(name, newlineIndices, coordinatesDCB);
      } else if(name.contains("short")) {
        r = calcShortCXCY(name, newlineIndices, canvasWidth, coordinatesDCB);
      }
    }
    return r;
  }

  private Result<DataClassBrick> calcCA(ArrayList<Integer> newlineIndices, CompoundDataClassBrick thisAsBrick) {
    Result<DataClassBrick> r;
    CompoundDataClassBrick longCXCYDCB = (CompoundDataClassBrick) thisAsBrick.getInner("longCXCY"); //assumes longCXCYToCA is easier than shortToCA
    if(longCXCYDCB.isComplete()) {
      r = calcCAFromLongCXCY(newlineIndices, thisAsBrick);
    } else {
      r = calcCAFromShortCXCY(newlineIndices, thisAsBrick);
    }
    return r;
  }

  private Result<DataClassBrick> calcLongCXCY(String name, ArrayList<Integer> newlineIndices, CompoundDataClassBrick thisAsBrick) {
    Result<DataClassBrick> r;
    PrimitiveDataClassBrick caDCB = (PrimitiveDataClassBrick) thisAsBrick.getInner("ca");
    if(caDCB.isComplete()) {
      r = calcLongCXCYFromCA(newlineIndices, thisAsBrick);
    } else {
      r = calcLongCXCYFromShortCXCY(newlineIndices, thisAsBrick);
    }
    CompoundDataClassBrick cxcyDCB = (CompoundDataClassBrick) r.getVal();
    if(name.equals("longCX")) {
      r.putVal(cxcyDCB.getInner("longCX"));
    } else if(name.equals("longCY")) {
      r.putVal(cxcyDCB.getInner("longCY"));
    }
    return r;
  }

  private Result<DataClassBrick> calcShortCXCY(String name, ArrayList<Integer> newlineIndices, int canvasWidth, CompoundDataClassBrick thisAsBrick) {
    Result<DataClassBrick> r;
    PrimitiveDataClassBrick caCXCYDCB = (PrimitiveDataClassBrick) thisAsBrick.getInner("ca");
    if(caCXCYDCB.isComplete()) {
      r = calcShortCXCYFromCA(newlineIndices, canvasWidth, thisAsBrick);
    } else {
      r = calcShortCXCYFromLongCXCY(newlineIndices, canvasWidth, thisAsBrick);
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
    CompoundDataClassBrick longCXCYDCB = (CompoundDataClassBrick) thisAsBrick.getInner("longCXCY");
    PrimitiveDataClassBrick longCXDCB = (PrimitiveDataClassBrick) longCXCYDCB.getInner("longCX");
    PrimitiveDataClassBrick longCYDCB = (PrimitiveDataClassBrick) longCXCYDCB.getInner("longCY");
    int longCX = (int) longCXDCB.getDFB().getVal();
    int longCY = (int) longCYDCB.getDFB().getVal();
    int ca = 0;
    if(longCY > 0) {
      ca += newlineIndices.get(longCY - 1) + 1;
    }
    ca += longCX;
    PrimitiveDataClassBrick caDCB = (PrimitiveDataClassBrick) thisAsBrick.getInner("ca");
    caDCB.putSafe(ca);
    return Result.make(caDCB, null);
  }

  private Result<DataClassBrick> calcCAFromShortCXCY(ArrayList<Integer> newlineIndices, CompoundDataClassBrick coordinatesDCB) {
    CompoundDataClassBrick shortCXCY = (CompoundDataClassBrick) coordinatesDCB.getInner("shortCXCY");
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
    PrimitiveDataClassBrick caDCB = (PrimitiveDataClassBrick) coordinatesDCB.getInner("ca");
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
    CompoundDataClassBrick longCXCYDCB = (CompoundDataClassBrick) thisAsBrick.getInner("longCXCY");
    PrimitiveDataClassBrick longCXDCB = (PrimitiveDataClassBrick) longCXCYDCB.getInner("longCX");
    PrimitiveDataClassBrick longCYDCB = (PrimitiveDataClassBrick) longCXCYDCB.getInner("longCY");
    longCXDCB.putSafe(cx);
    longCYDCB.putSafe(cy);
    return Result.make(longCXCYDCB, null);
  }

  private Result<DataClassBrick> calcLongCXCYFromShortCXCY(ArrayList<Integer> newlineIndices, CompoundDataClassBrick coordinatesDCB) { //harder than calculating long from ca
    CompoundDataClassBrick shortCXCYDCB = (CompoundDataClassBrick) coordinatesDCB.getInner("shortCXCY");
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
    CompoundDataClassBrick longCXCYDCB = (CompoundDataClassBrick) coordinatesDCB.getInner("longCXCY");
    PrimitiveDataClassBrick longCXDCB = (PrimitiveDataClassBrick) longCXCYDCB.getInner("longCX");
    PrimitiveDataClassBrick longCYDCB = (PrimitiveDataClassBrick) longCXCYDCB.getInner("longCY");
    longCXDCB.putSafe(longCX);
    longCYDCB.putSafe(longCY);
    return Result.make(longCXCYDCB, null);
  }

  private Result<DataClassBrick> calcShortCXCYFromCA(ArrayList<Integer> newlineIndices, int canvasWidth, CompoundDataClassBrick coordinatesDCB) {
    CompoundDataClassBrick shortCXCYDCB = (CompoundDataClassBrick) coordinatesDCB.getInner("shortCXCY");
    PrimitiveDataClassBrick caDCB = (PrimitiveDataClassBrick) coordinatesDCB.getInner("ca");
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
    if(ca > newlineIndices.get(i)) {
      shortCX = (newlineIndices.get(i) - ca) % canvasWidth;
    } else {
      shortCX = ca;
    }
    PrimitiveDataClassBrick shortCXDCB = (PrimitiveDataClassBrick) shortCXCYDCB.getInner("shortCX");
    PrimitiveDataClassBrick shortCYDCB = (PrimitiveDataClassBrick) shortCXCYDCB.getInner("shortCY");
    shortCXDCB.putSafe(shortCX);
    shortCYDCB.putSafe(shortCY);
    return Result.make(shortCXCYDCB, null);
  }

  private Result<DataClassBrick> calcShortCXCYFromLongCXCY(ArrayList<Integer> newlineIndices, int canvasWidth, CompoundDataClassBrick coordinatesDCB) {
    CompoundDataClassBrick longCXCYDCB = (CompoundDataClassBrick) coordinatesDCB.getInner("longCXCY");
    CompoundDataClassBrick shortCXCYDCB = (CompoundDataClassBrick) coordinatesDCB.getInner("shortCXCY");
    PrimitiveDataClassBrick longCXDCB = (PrimitiveDataClassBrick) longCXCYDCB.getInner("longCX");
    PrimitiveDataClassBrick longCYDCB = (PrimitiveDataClassBrick) longCXCYDCB.getInner("longCY");
    int longCX = (int)longCXDCB.getVal();
    int longCY = (int)longCYDCB.getVal();
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
