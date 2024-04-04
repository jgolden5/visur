package com.ple.visur;

import java.util.Objects;

public class DataFormBrick {
  DataForm fromDF;
  Object val;


  private DataFormBrick(DataForm fromDF, Object val) {
    this.fromDF = fromDF;
    this.val = val;
  }

  public static DataFormBrick make(DataForm fromDF, Object val) {
    return new DataFormBrick(fromDF, val);
  }

  public DataFormBrick convert(DataForm toDF) {
    Object newDFBVal = fromDF.convert(toDF, val);
    return new DataFormBrick(toDF, newDFBVal);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    DataFormBrick that = (DataFormBrick) o;
    boolean thisValEqualsThatVal;
    if(val == null) {
      thisValEqualsThatVal = val == that.val;
    } else {
      thisValEqualsThatVal = val.equals(that.val);
    }
    return fromDF.equals(that.fromDF) && thisValEqualsThatVal;
  }

  @Override
  public int hashCode() {
    return Objects.hash(fromDF, val);
  }

  public Object getVal() {
    return val;
  }

  public DataForm getDF() {
    return fromDF;
  }
}
