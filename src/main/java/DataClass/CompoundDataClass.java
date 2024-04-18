package DataClass;

import java.util.HashMap;

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
  public abstract DataClassBrick derive(CompoundDataClassBrick cdcb, String innerName, DCHolder dcHolder); //where dcb = the dcb being derived
  public abstract CompoundDataClassBrick makeBrick(DCHolder dcHolder, CompoundDataClassBrick outer);
}
