package com.ple.visur;

import CursorPositionDC.WholeNumberDC;
import DataClass.PrimitiveDataClassBrick;
import DataClass.Result;

public class BrickVisurVar implements VisurVar {
  PrimitiveDataClassBrick brick;

  public static BrickVisurVar make(PrimitiveDataClassBrick brick) {
    return new BrickVisurVar(brick);
  }

  public BrickVisurVar(PrimitiveDataClassBrick brick) {
    this.brick = brick;
  }

  @Override
  public Object getVal() {
    if(brick.isComplete()) {
      return brick.get().getVal();
    } else {
      return getUnset();
    }
  }

  @Override
  public Result putVal(Object o) {
    return brick.putForce(o);
  }

  public boolean isComplete() {
    return brick.isComplete();
  }

  public Object getUnset() {
    if(brick.getPDC() instanceof WholeNumberDC) {
      return -1;
    } else {
      return null;
    }
  }

}
