package com.ple.visur;


public abstract class DataClassBrick {
  DataClass dc;
  CompoundDataClassBrick parent;
  public DataClassBrick(DataClass dc, CompoundDataClassBrick parent) {
    this.dc = dc;
    this.parent = parent;
  }
  public CompoundDataClassBrick getParent() {
    return parent;
  }
}
