package CursorPositionDC;

import DataClass.DCHolder;
import DataClass.DataForm;

public class CursorPositionDCHolder extends DCHolder {

  public DataForm aDF = new Adf(1);
  public DataForm cxcyDF = new CXCYdf(1);

  public static CursorPositionDCHolder make() {
    return new CursorPositionDCHolder();
  }

}
