package DataClass;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Stack;

public abstract class OuterDataClass implements DataClass {

  public abstract OuterDataClassBrick makeBrick(String name, ArrayList<OuterDataClassBrick> outers, PrimitiveDataClassBrick... reusablePDCBs);

  public abstract Result<Object> calcInternal(String targetName, OuterDataClassBrick thisAsBrick);

  public void removeConflicts(OuterDataClassBrick thisAsBrick) {
    thisAsBrick.remove();
  }

}
