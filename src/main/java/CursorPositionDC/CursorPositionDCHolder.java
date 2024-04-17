package CursorPositionDC;

import DataClass.DCHolder;

public class CursorPositionDCHolder extends DCHolder {

  public JavaIntDF javaIntDF = new JavaIntDF(0);
  public CoordinateDF coordinateDF = new CoordinateDF(0);
  public CursorPosDC cursorPosDC = new CursorPosDC(1);
  public WholeNumberDC aDC = new WholeNumberDC();
  public WholePairDC cxcyDC = new WholePairDC(2);
  public WholeNumberDC cxDC = new WholeNumberDC();
  public WholeNumberDC cyDC = new WholeNumberDC();

  public static CursorPositionDCHolder make() {
    return new CursorPositionDCHolder();
  }

}
