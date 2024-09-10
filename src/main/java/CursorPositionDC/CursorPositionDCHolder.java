package CursorPositionDC;

import DataClass.DCHolder;
import io.vertx.core.shareddata.Shareable;

public class CursorPositionDCHolder extends DCHolder implements Shareable {

  public final JavaIntDF javaIntDF = new JavaIntDF(0);
  public final IntArrayListDF intArrayListDF = new IntArrayListDF(0);
  public final CursorPositionDC cursorPositionDC = new CursorPositionDC();
  public final CoordinatesDC coordinatesDC = new CoordinatesDC(1);
  public final CAAndNLDC caAndNLDC = new CAAndNLDC(2);
  public final RCXCYAndNLDC rcxcyAndNLDC = new RCXCYAndNLDC(3);
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
    coordinatesDC.putInner("caAndNL", caAndNLDC);
    coordinatesDC.putInner("rcxcyAndNL", rcxcyAndNLDC);
    cursorPositionDC.putLayer(coordinatesDC);
  }

}
