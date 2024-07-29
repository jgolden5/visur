package DataClass;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public abstract class CompoundDataClass extends OuterDataClass {
  HashMap<String, DataClass> inners = new HashMap<>();
  int requiredSetValues;
  public CompoundDataClass(int requiredSetValues) {
    this.requiredSetValues = requiredSetValues;
  }

  public DataClass getInner(String innerName) {
    return inners.get(innerName);
  }
  public void putInner(String innerName, DataClass innerVal) {
    inners.put(innerName, innerVal);
  }

  @Override
  public abstract Result<DataClassBrick> calcInternal(String name, DataClassBrick outerAsBrick);

}
