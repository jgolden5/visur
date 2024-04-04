package com.ple.visur;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class CompoundDataClassBrick extends DataClassBrick {
  private final CompoundDataClass dc;
  private final CompoundDataClassBrick parent;
  private final HashMap<String, DataClassBrick> subs;
  private final int minimumRequiredSetValues;

  private CompoundDataClassBrick(CompoundDataClass dc, CompoundDataClassBrick parent, HashMap<String, DataClassBrick> subs, int minimumRequiredSetValues) {
    super(dc, parent);
    this.dc = dc;
    this.parent = parent;
    this.subs = subs;
    this.minimumRequiredSetValues = minimumRequiredSetValues;
  }

  public static CompoundDataClassBrick make(CompoundDataClass dc, CompoundDataClassBrick parent, HashMap<String, DataClassBrick> subs, int minimumRequiredSetValues) {
    return new CompoundDataClassBrick(dc, parent, subs, minimumRequiredSetValues);
  }

  public CompoundDataClassBrick putSub(String name, DataClassBrick dcb) {
    HashMap<String, DataClassBrick> newSubs = new HashMap<>();
    for(HashMap.Entry<String, DataClassBrick> entry : subs.entrySet()) {
      newSubs.put(entry.getKey(), entry.getValue());
    }
    newSubs.put(name, dcb);
    return make(dc, parent, newSubs, minimumRequiredSetValues);
  }

  public DataClassBrick getSub(String name) {
    if(sufficientNumberOfValuesAreSet()) {
      DataClassBrick targetSub = subs.get(name);
      if (targetSub instanceof PrimitiveDataClassBrick) {
        DataFormBrick targetSubDFB = ((PrimitiveDataClassBrick) targetSub).getVal();
        Object targetSubDFBVal = targetSubDFB.getVal();
        if (targetSubDFBVal == null) {
          PrimitiveDataClass targetSubDC = (PrimitiveDataClass) dc.getSub(name);
          Deriver deriver = dc.getDeriver(name);
          DataFormBrick targetSubVal = deriver.derive(this);
          targetSub = PrimitiveDataClassBrick.make(targetSubDC, targetSubVal);
        }
      }
      return targetSub;
    } else {
      return null;
    }
  }

  public boolean sufficientNumberOfValuesAreSet() {
    int numberOfSetValues = 0;
    for(Map.Entry<String, DataClassBrick> entry : subs.entrySet()) {
      PrimitiveDataClassBrick entryVal = (PrimitiveDataClassBrick) entry.getValue();
      DataFormBrick dfb = entryVal.getVal();
      Object dfbVal = dfb.getVal();
      numberOfSetValues += dfbVal != null ? 1 : 0;
    }
    return numberOfSetValues >= minimumRequiredSetValues;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof CompoundDataClassBrick that)) return false;
    return Objects.equals(dc, that.dc) && Objects.equals(subs, that.subs);
  }

  @Override
  public int hashCode() {
    return Objects.hash(dc, subs);
  }

}
