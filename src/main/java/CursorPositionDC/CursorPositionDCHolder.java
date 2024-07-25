package CursorPositionDC;

import DataClass.DCHolder;
import io.vertx.core.shareddata.Shareable;

public class CursorPositionDCHolder extends DCHolder implements Shareable {

  public final JavaIntDF javaIntDF = new JavaIntDF(0);
  public final IntArrayListDF intArrayListDF = new IntArrayListDF(0);
  public final CursorPositionDC cursorPositionDC = new CursorPositionDC();
  public final WholeNumberDC wholeNumberDC = new WholeNumberDC(javaIntDF);
  public final WholeNumberListDC wholeNumberListDC = new WholeNumberListDC(intArrayListDF);

  public static CursorPositionDCHolder make() {
    return new CursorPositionDCHolder();
  }

  public CursorPositionDCHolder() {
    //set up dc layers and inners hierarchy here
  }

}
