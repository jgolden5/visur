package com.ple.visur;

import CursorPositionDC.WholeNumberDC;
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
    if(!brick.isComplete()) {
      Result<DataClassBrick> calculatedBrickResult = brick.getOrCalc();
      PrimitiveDataClassBrick calculatedBrick = (PrimitiveDataClassBrick) calculatedBrickResult.getVal();
      brick = calculatedBrick;
    }
    return brick.get().getVal();
  }

  @Override
  public Result putVal(Object o) {
    brick.putForce(o);
    return Result.make();
  }

  public boolean isComplete() {
    return brick.isComplete();
  }

  public Object getUnset() {
    return null; //may change
  }

}
