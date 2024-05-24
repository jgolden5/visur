package com.ple.visur;

import DataClass.DataClassBrick;
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
    return brick.get().getVal();
  }

  @Override
  public Result putVal(Object o) {
    return brick.putForce(o);
  }

  public boolean isComplete() {
    return brick.isComplete();
  }

}
