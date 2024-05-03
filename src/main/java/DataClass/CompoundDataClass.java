package DataClass;

import CursorPositionDC.CursorPositionDCHolder;
import com.ple.visur.Result;

import java.util.HashMap;
import java.util.Map;

public abstract class CompoundDataClass implements DataClass {
  HashMap<String, DataClass> inners = new HashMap<>();
  int minimumRequiredSetValues;
  public CompoundDataClass(int minimumRequiredSetValues) {
    this.minimumRequiredSetValues = minimumRequiredSetValues;
  }
  public boolean minimumValuesAreSet(CompoundDataClassBrick cdcb, DCHolder dcHolder) {
    int setValues = 0;
    for(DataClassBrick innerPDCB : cdcb.inners.values()) {
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

  public abstract Result<DataClassBrick> calculateInnerBrick(String name, CompoundDataClassBrick compoundDataClassBrick, CursorPositionDCHolder cursorPositionDCHolder);

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
