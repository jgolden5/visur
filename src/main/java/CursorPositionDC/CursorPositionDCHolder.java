package CursorPositionDC;

import DataClass.DCHolder;
import DataClass.DataClass;
import io.vertx.core.shareddata.Shareable;

public class CursorPositionDCHolder extends DCHolder implements Shareable {

  public final JavaIntDF javaIntDF = new JavaIntDF(0);
  public final IntArrayListDF intArrayListDF = new IntArrayListDF(0);
  public final CursorPositionDC cursorPositionDC = new CursorPositionDC(2);
  public final WholeNumberDC wholeNumberDC = new WholeNumberDC(javaIntDF);
  public final LongCXCYDC longCXCYDC = new LongCXCYDC(2);
  public final ShortCXCYDC shortCXCYDC = new ShortCXCYDC(3);
  public final CXCYCADC cxcycaDC = new CXCYCADC(1);
  public final WholePairDC wholePairDC = new WholePairDC(2);
  public final WholeNumberListDC wholeNumberListDC = new WholeNumberListDC(intArrayListDF);

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
