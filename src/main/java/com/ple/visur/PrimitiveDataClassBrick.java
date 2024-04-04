package com.ple.visur;

import java.util.Objects;

public class PrimitiveDataClassBrick extends DataClassBrick {
  PrimitiveDataClass dc;
  CompoundDataClassBrick parent;
  DataFormBrick val;

  public PrimitiveDataClassBrick(PrimitiveDataClass dc, CompoundDataClassBrick parent, DataFormBrick val) {
    super(dc, parent);
    this.dc = dc;
    this.parent = parent;
    this.val = val;
  }

  public static PrimitiveDataClassBrick make(PrimitiveDataClass pdc, CompoundDataClassBrick parent, DataFormBrick dfbVal) {
    DataFormBrick referenceDFB = pdc.getReference(dfbVal);
    return new PrimitiveDataClassBrick(pdc, parent, referenceDFB);
  }

  public void putVal(DataFormBrick dfb) {
    this.val = dfb;
  }

  public DataFormBrick getVal(DataForm desiredDF) {
    return val.convert(desiredDF);
  }

  public DataFormBrick getVal() {
    return val;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof PrimitiveDataClassBrick that)) return false;
    return Objects.equals(parent, that.parent) && Objects.equals(val, that.val);
  }

  @Override
  public int hashCode() {
    return Objects.hash(parent, val);
  }
}
