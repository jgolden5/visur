package CursorPositionDC;

import DataClass.DCHolder;

public class CursorPositionDCHolder extends DCHolder {

  public JavaIntDF javaIntDF = new JavaIntDF(0);
  public CoordinateDF coordinateDF = new CoordinateDF(0);
  public IntArrayListDF intArrayListDF = new IntArrayListDF(0);
  public CursorPositionDC cursorPositionDC = new CursorPositionDC(2);
  public WholeNumberDC wholeNumberDC = new WholeNumberDC();
  public WholePairDC wholePairDC = new WholePairDC(2);
  public WholeNumberListDC wholeNumberListDC = new WholeNumberListDC();
  public CXCYCADC cxcycaDC = new CXCYCADC(1);

  public static CursorPositionDCHolder make() {
    return new CursorPositionDCHolder();
  }

  public CursorPositionDCHolder() {
    wholePairDC.putInner("cx", wholeNumberDC);
    wholePairDC.putInner("cy", wholeNumberDC);
    cxcycaDC.putInner("cxcy", wholePairDC);
    cxcycaDC.putInner("a", wholeNumberDC);
    cursorPositionDC.putInner("ni", wholeNumberListDC);
    cursorPositionDC.putInner("cxcyca", cxcycaDC);
  }

}
