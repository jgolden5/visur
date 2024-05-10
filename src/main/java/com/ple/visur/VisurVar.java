package com.ple.visur;

public interface VisurVar {
  public Object getVal();
  public void putVal(Object obj);

  public static VisurVar make() {
    return null;
  }
}
