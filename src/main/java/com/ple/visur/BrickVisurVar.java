package com.ple.visur;

import DataClass.PrimitiveDataClassBrick;
import DataClass.Result;

import java.util.HashSet;

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
    if(!brick.isComplete()) {
      brick.getOrCalc(new HashSet<>());
    }
    return brick.getVal();
  }

  @Override
  public Result putVal(Object v) {
    brick.put(v);
    return Result.make();
  }

  public boolean isComplete() {
    return brick.isComplete();
  }

  public Object getUnset() {
    return null; //may change
  }

}
