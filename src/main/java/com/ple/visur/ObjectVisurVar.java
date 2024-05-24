package com.ple.visur;

import DataClass.Result;

public class ObjectVisurVar implements VisurVar {
  Object val;
  public static ObjectVisurVar make(Object val) {
    return new ObjectVisurVar(val);
  }

  public ObjectVisurVar(Object val) {
    this.val = val;
  }

  @Override
  public Object getVal() {
    return val;
  }

  @Override
  public Result putVal(Object obj) {
    val = obj;
    return null;
  }
}
