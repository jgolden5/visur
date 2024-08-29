package CursorPositionDC;

import DataClass.DCHolder;
import io.vertx.core.shareddata.Shareable;

public class CursorPositionDCHolder extends DCHolder implements Shareable {

  public final JavaIntDF javaIntDF = new JavaIntDF(0);
  public final IntArrayListDF intArrayListDF = new IntArrayListDF(0);
  public final CursorPositionDC cursorPositionDC = new CursorPositionDC();
  public final CoordinatesDC coordinatesDC = new CoordinatesDC(1);
  public final LLFromCYDC llFromCYDC = new LLFromCYDC(1);
  public final VirtualDC virtualDC = new VirtualDC(2);
  public final CAAndNLDC caAndNLDC = new CAAndNLDC(2);
  public final RCXCYAndNLDC rcxcyAndNLDC = new RCXCYAndNLDC(3);
  public final CYAndNLDC cyAndNLDC = new CYAndNLDC(2);
  public final LLCYAndNLDC llcyAndNLDC = new LLCYAndNLDC(3);
  public final VCXRCXAndLODC vcxRcxAndLODC = new VCXRCXAndLODC(2);
  public final WholeNumberDC wholeNumberDC = new WholeNumberDC(javaIntDF);
  public final WholeNumberListDC wholeNumberListDC = new WholeNumberListDC(intArrayListDF);

  public static CursorPositionDCHolder make() {
    return new CursorPositionDCHolder();
  }

  public CursorPositionDCHolder() {
    caAndNLDC.putInner("wholeNumber", wholeNumberDC);
    caAndNLDC.putInner("nl", wholeNumberListDC);
    rcxcyAndNLDC.putInner("wholeNumber", wholeNumberDC);
    rcxcyAndNLDC.putInner("nl", wholeNumberListDC);
    cyAndNLDC.putInner("wholeNumber", wholeNumberDC);
    cyAndNLDC.putInner("nl", wholeNumberListDC);
    llcyAndNLDC.putInner("wholeNumber", wholeNumberDC);
    llcyAndNLDC.putInner("nl", wholeNumberListDC);
    vcxRcxAndLODC.putInner("wholeNumber", wholeNumberDC);
    coordinatesDC.putInner("caAndNL", caAndNLDC);
    coordinatesDC.putInner("rcxcyAndNL", rcxcyAndNLDC);
    llFromCYDC.putInner("cyAndNL", cyAndNLDC);
    llFromCYDC.putInner("llcyAndNL", llcyAndNLDC);
    virtualDC.putInner("wholeNumber", wholeNumberDC);
    virtualDC.putInner("vcxRCXAndLO", vcxRcxAndLODC);
    cursorPositionDC.putLayer(coordinatesDC);
    cursorPositionDC.putLayer(llFromCYDC);
    cursorPositionDC.putLayer(virtualDC);
  }

}
