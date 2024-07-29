package DataClass;

import java.util.ArrayList;
import java.util.HashMap;

public abstract class OuterDataClass implements DataClass {

  public abstract OuterDataClassBrick makeBrick(String name, ArrayList<OuterDataClassBrick> outers, PrimitiveDataClassBrick... reusablePDCBs);

  public abstract Result<DataClassBrick> calcInternal(String name, DataClassBrick brick);

  public void removeConflicts(OuterDataClassBrick thisAsBrick) {
    thisAsBrick.remove();
  }

}
