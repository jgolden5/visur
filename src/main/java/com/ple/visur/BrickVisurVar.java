package com.ple.visur;

import DataClass.PrimitiveDataClassBrick;
import DataClass.Result;

import java.util.ArrayList;

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
      Result<PrimitiveDataClassBrick> calculatedBrickResult = brick.getOrCalc(new ArrayList<>());
      PrimitiveDataClassBrick calculatedBrick = calculatedBrickResult.getVal();
      brick = calculatedBrick;
    }
    return brick.getVal();
  }

  @Override
  public Result putVal(Object o) {
    brick.put(o);
    return Result.make();
  }

  public boolean isComplete() {
    return brick.isComplete();
  }

  public Object getUnset() {
    return null; //may change
  }

}
