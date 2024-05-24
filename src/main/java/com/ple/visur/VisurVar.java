package com.ple.visur;

import DataClass.Result;

public interface VisurVar {
  public Object getVal();
  public Result putVal(Object obj);

  public static VisurVar make() {
    return null;
  }
}
