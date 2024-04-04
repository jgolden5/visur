package com.ple.visur;

import java.util.Objects;

public class PrimitiveDataClassBrick extends DataClassBrick {
  PrimitiveDataClass fromDC;
  DataFormBrick val;

  public PrimitiveDataClassBrick(PrimitiveDataClass fromDC, DataFormBrick val) {
    super(fromDC);
    this.fromDC = fromDC;
    this.val = val;
  }

  public static PrimitiveDataClassBrick make(PrimitiveDataClass pdc, DataFormBrick dfbVal) {
    DataFormBrick referenceDFB = pdc.getReference(dfbVal);
    return new PrimitiveDataClassBrick(pdc, referenceDFB);
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
    return Objects.equals(fromDC, that.fromDC) && Objects.equals(val, that.val);
  }

  @Override
  public int hashCode() {
    return Objects.hash(fromDC, val);
  }
}
