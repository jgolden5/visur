package com.ple.visur;


public abstract class DataClassBrick {
  DataClass parent;
  public DataClassBrick(DataClass parent) {
    this.parent = parent;
  }
  public DataClass getParent() {
    return parent;
  }
}
