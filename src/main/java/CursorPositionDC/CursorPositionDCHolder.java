package CursorPositionDC;

import DataClass.DCHolder;
import DataClass.DataForm;

public class CursorPositionDCHolder extends DCHolder {

  public DataForm javaIntDF = new JavaIntDF(0);
  public DataForm cursorPosDF = new CursorPosDF(0);

  public static CursorPositionDCHolder make() {
    return new CursorPositionDCHolder();
  }

}
