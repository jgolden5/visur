package DataClass;

import java.util.HashMap;
import java.util.Map;

public abstract class CompoundDataClass implements DataClass {
  HashMap<String, DataClass> inners = new HashMap<>();
  int minimumRequiredSetValues;
  public CompoundDataClass(int minimumRequiredSetValues) {
    this.minimumRequiredSetValues = minimumRequiredSetValues;
  }
  public boolean checkCanSet(CompoundDataClassBrick thisAsBrick, CompoundDataClassBrick outer, DCHolder dcHolder) {
    boolean canSetThisBrick = minimumValuesAreSet(thisAsBrick, dcHolder);
    boolean outerBrickIsSet = true;
    if(outer != null) {
      outerBrickIsSet = outer.getCDC().minimumValuesAreSet(outer, dcHolder);
    }
    return canSetThisBrick && outerBrickIsSet;
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
  public abstract CompoundDataClassBrick makeBrick(DCHolder dcHolder, CompoundDataClassBrick outer);

  public abstract Result<DataClassBrick> calculateInternal(String innerName, CompoundDataClassBrick thisAsBrick, DCHolder dcHolder);
  public Result<DataClassBrick> calculate(String innerName, CompoundDataClassBrick thisAsBrick, DCHolder dcHolder) {
    Result<DataClassBrick> r = calculateInternal(innerName, thisAsBrick, dcHolder);
    while(r.getError() != null && thisAsBrick.getOuter() != null) {
      if (r.getError() != null) {
        r = thisAsBrick.getOuter().calculate(innerName, dcHolder);
      }
    }
    return r;
  }
  public boolean brickCanBeSet(String nameOfThisBrick, HashMap<String, DataClassBrick> innerBricks) {
    int numberOfSetValues = 1; //represents by the value that is currently being set
    for(Map.Entry innerBrick : innerBricks.entrySet()) {
      if(innerBrick.getValue() != null && !innerBrick.getKey().equals(nameOfThisBrick)) {
        numberOfSetValues++;
      }
    }
    return numberOfSetValues <= minimumRequiredSetValues;
  }

}
