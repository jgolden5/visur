package CursorPositionDC;

import DataClass.DCHolder;
import io.vertx.core.shareddata.Shareable;

public class CursorPositionDCHolder extends DCHolder implements Shareable {

  public final JavaIntDF javaIntDF = new JavaIntDF(0);
  public final IntArrayListDF intArrayListDF = new IntArrayListDF(0);
  public final CursorPositionDC cursorPositionDC = new CursorPositionDC(3);
  public final WholeNumberDC wholeNumberDC = new WholeNumberDC(javaIntDF);
  public final CXCYDC CXCYDC = new CXCYDC(2);
  public final CoordinatesDC coordinatesDC = new CoordinatesDC(1);
  public final WholePairDC wholePairDC = new WholePairDC(2);
  public final WholeNumberListDC wholeNumberListDC = new WholeNumberListDC(intArrayListDF);

  public static CursorPositionDCHolder make() {
    return new CursorPositionDCHolder();
  }

  public CursorPositionDCHolder() {
    CXCYDC.putInner("wholeNumber", wholeNumberDC);
    coordinatesDC.putInner("cxcy", CXCYDC);
    coordinatesDC.putInner("wholeNumber", wholeNumberDC);
    cursorPositionDC.putInner("wholeNumber", wholeNumberDC);
    cursorPositionDC.putInner("ni", wholeNumberListDC);
    cursorPositionDC.putInner("coordinates", coordinatesDC);
  }

}
