package com.ple.visur;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class CompoundDataClassBrick extends DataClassBrick {
  private final CompoundDataClass fromDC;
  private final HashMap<String, DataClassBrick> subs;
  private final int minimumRequiredSetValues;

  private CompoundDataClassBrick(CompoundDataClass fromDC, HashMap<String, DataClassBrick> subs, int minimumRequiredSetValues) {
    super(fromDC);
    this.fromDC = fromDC;
    this.subs = subs;
    this.minimumRequiredSetValues = minimumRequiredSetValues;
  }

  public static CompoundDataClassBrick make(CompoundDataClass dc, HashMap<String, DataClassBrick> subs, int minimumRequiredSetValues) {
    return new CompoundDataClassBrick(dc, subs, minimumRequiredSetValues);
  }

  public CompoundDataClassBrick putSub(String name, DataClassBrick dcb) {
    HashMap<String, DataClassBrick> newSubs = new HashMap<>();
    for(HashMap.Entry<String, DataClassBrick> entry : subs.entrySet()) {
      newSubs.put(entry.getKey(), entry.getValue());
    }
    newSubs.put(name, dcb);
    return make(fromDC, newSubs, minimumRequiredSetValues);
  }

  public DataClassBrick getSub(String name) {
    if(sufficientNumberOfValuesAreSet()) {
      DataClassBrick targetSub = subs.get(name);
      if (targetSub instanceof PrimitiveDataClassBrick) {
        DataFormBrick targetSubDFB = ((PrimitiveDataClassBrick) targetSub).getVal();
        Object targetSubDFBVal = targetSubDFB.getVal();
        if (targetSubDFBVal == null) {
          PrimitiveDataClass targetSubDC = (PrimitiveDataClass) fromDC.getSub(name);
          Deriver deriver = fromDC.getDeriver(name);
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
    return Objects.equals(fromDC, that.fromDC) && Objects.equals(subs, that.subs);
  }

  @Override
  public int hashCode() {
    return Objects.hash(fromDC, subs);
  }

}
