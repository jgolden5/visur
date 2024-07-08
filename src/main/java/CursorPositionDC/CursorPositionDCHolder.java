package CursorPositionDC;

import DataClass.DCHolder;
import io.vertx.core.shareddata.Shareable;

public class CursorPositionDCHolder extends DCHolder implements Shareable {

  public JavaIntDF javaIntDF = new JavaIntDF(0);
  public IntArrayListDF intArrayListDF = new IntArrayListDF(0);
  public CursorPositionDC cursorPositionDC = new CursorPositionDC(2);
  public WholeNumberDC wholeNumberDC = new WholeNumberDC(javaIntDF);
  public CXCYCWDC cxcycwDC = new CXCYCWDC(2);
  public CXCYDC cxcyDC = new CXCYDC(1);
  public WholePairDC wholePairDC = new WholePairDC(2);
  public WholeNumberListDC wholeNumberListDC = new WholeNumberListDC(intArrayListDF);
  public CXCYCADC cxcycaDC = new CXCYCADC(1);

  public static CursorPositionDCHolder make() {
    return new CursorPositionDCHolder();
  }

  public CursorPositionDCHolder() {
    wholePairDC.putInner("wholeNumber", wholeNumberDC);
    cxcyDC.putInner("wholePair", wholePairDC);
    cxcycwDC.putInner("wholeNumber", wholeNumberDC);
    cxcycwDC.putInner("cxcy", cxcyDC);
    cxcycaDC.putInner("cxcycw", cxcycwDC);
    cxcycaDC.putInner("wholeNumber", wholeNumberDC);
    cursorPositionDC.putInner("ni", wholeNumberListDC);
    cursorPositionDC.putInner("cxcyca", cxcycaDC);
  }

}
