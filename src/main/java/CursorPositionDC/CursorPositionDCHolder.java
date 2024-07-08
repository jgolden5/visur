package CursorPositionDC;

import DataClass.DCHolder;
import DataClass.DataClass;
import io.vertx.core.shareddata.Shareable;

public class CursorPositionDCHolder extends DCHolder implements Shareable {

  private final JavaIntDF javaIntDF = new JavaIntDF(0);
  private final IntArrayListDF intArrayListDF = new IntArrayListDF(0);
  private final CursorPositionDC cursorPositionDC = new CursorPositionDC(2);
  private final WholeNumberDC wholeNumberDC = new WholeNumberDC(javaIntDF);
  private final LongCXCYDC longCXCYDC = new LongCXCYDC(2);
  private final ShortCXCYDC shortCXCYDC = new ShortCXCYDC(3);
  private final CXCYCADC cxcycaDC = new CXCYCADC(1);
  private final WholePairDC wholePairDC = new WholePairDC(2);
  private final WholeNumberListDC wholeNumberListDC = new WholeNumberListDC(intArrayListDF);

  public static CursorPositionDCHolder make() {
    return new CursorPositionDCHolder();
  }

  public CursorPositionDCHolder() {
    shortCXCYDC.putInner("wholeNumber", wholeNumberDC);
    longCXCYDC.putInner("wholeNumber", wholeNumberDC);
    cxcycaDC.putInner("shortCXCY", shortCXCYDC);
    cxcycaDC.putInner("longCXCY", longCXCYDC);
    cxcycaDC.putInner("wholeNumber", wholeNumberDC);
    cursorPositionDC.putInner("ni", wholeNumberListDC);
    cursorPositionDC.putInner("cxcyca", cxcycaDC);
  }

}
