package com.ple.visur;

public class IntVisurVar implements VisurVar {
  int val;
  public IntVisurVar(int val) {
    this.val = val;
  }

  @Override
  public String getString() {
    return String.valueOf(val);
  }

  @Override
  public int getInt() {
    return val;
  }

  @Override
  public boolean getBoolean() {
    return val == 0;
  }

  @Override
  public void put(String v) {
    val = Integer.parseInt(v);
  }

  @Override
  public void put(int v) {
    val = v;
  }

  @Override
  public void put(boolean v) {
    if(v == true) {
      val = 0;
    } else {
      val = 1;
    }
  }

  @Override
  public void put(Object v) {
    switch(v) {
      case null ->
        val = Integer.parseInt(null);
      case String s ->
        val = Integer.parseInt(s);
      case Integer i ->
        val = i;
      case Boolean b ->
        val = b ? 0 : 1;
      default -> throw new IllegalStateException("Unexpected value: " + v);
    }
  }

}
