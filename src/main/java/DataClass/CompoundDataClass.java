package DataClass;

import java.util.HashMap;
import java.util.Map;

public abstract class CompoundDataClass implements DataClass {
  HashMap<String, DataClass> inners = new HashMap<>();
  int minimumRequiredSetValues;
  public CompoundDataClass(int minimumRequiredSetValues) {
    this.minimumRequiredSetValues = minimumRequiredSetValues;
  }
  public abstract DataClassBrick makeBrick();
  public boolean checkCanSet(CompoundDataClassBrick thisAsBrick, CompoundDataClassBrick outer, DCHolder dcHolder) {
    boolean thisBrickIsSet = minimumValuesAreSet(thisAsBrick, dcHolder);
    boolean outerBrickIsSet = true;
    if(outer != null) {
      outerBrickIsSet = outer.getCDC().minimumValuesAreSet(outer, dcHolder);
    }
    return thisBrickIsSet && outerBrickIsSet;
  }
  public boolean minimumValuesAreSet(CompoundDataClassBrick thisAsBrick, DCHolder dcHolder) {
    int setValues = 0;
    for(DataClassBrick innerPDCB : thisAsBrick.inners.values()) {
      if(innerPDCB != null) {
        setValues++;
      }
    }
    return setValues >= minimumRequiredSetValues;
  }
  public DataClass getInner(String innerName) {
      return inners.get(innerName);
  }
  public void putInner(String innerName, DataClass innerVal) {
    inners.put(innerName, innerVal);
  }
  public abstract Result<DataClassBrick> calcInternal(String name, CompoundDataClassBrick outerAsBrick);

  public abstract boolean conflicts(CompoundDataClassBrick brick);

}
