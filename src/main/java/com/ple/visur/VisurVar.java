package com.ple.visur;

import DataClass.DataClassBrick;

public class VisurVar {
  Object obj;
  DataClassBrick brick;
  public Object getObj() {
    return obj;
  }
  public DataClassBrick getBrick() {
    return brick;
  }
  public void putObj(Object obj) {
    this.obj = obj;
  }
  public void putBrick(DataClassBrick brick) {
    this.brick = brick;
  }
}
