package CursorPositionDC;

import DataClass.DCHolder;
import io.vertx.core.shareddata.Shareable;

public class CursorPositionDCHolder extends DCHolder implements Shareable {

  public JavaIntDF javaIntDF = new JavaIntDF(0);
  public IntArrayListDF intArrayListDF = new IntArrayListDF(0);
  public CursorPositionDC cursorPositionDC = new CursorPositionDC(2);
  public WholeNumberDC wholeNumberDC = new WholeNumberDC(javaIntDF);
  public WholePairDC wholePairDC = new WholePairDC(2);
  public WholeNumberListDC wholeNumberListDC = new WholeNumberListDC(intArrayListDF);
  public CXCYCADC cxcycaDC = new CXCYCADC(1);

  public static CursorPositionDCHolder make() {
    return new CursorPositionDCHolder();
  }

  public CursorPositionDCHolder() {
    wholePairDC.putInner("wholeNumber", wholeNumberDC);
    cxcycaDC.putInner("cxcy", wholePairDC);
    cxcycaDC.putInner("wholeNumber", wholeNumberDC);
    cursorPositionDC.putInner("ni", wholeNumberListDC);
    cursorPositionDC.putInner("cxcyca", cxcycaDC);
  }

}
