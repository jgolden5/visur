package com.ple.visur;

import DataClass.DataClassBrick;

public class BrickVisurVar implements VisurVar {
  DataClassBrick val;

  public static BrickVisurVar make(DataClassBrick val) {
    return new BrickVisurVar(val);
  }

  public BrickVisurVar(DataClassBrick val) {
    this.val = val;
  }

  @Override
  public DataClassBrick getVal() {
    return val;
  }

  @Override
  public void putVal(Object brick) {
    val = (DataClassBrick) brick;
  }

}
