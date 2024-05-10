package com.ple.visur;

import CursorPositionDC.CursorPositionDCHolder;
import DataClass.DataClassBrick;
import DataClass.Result;

public class BrickVisurVar implements VisurVar {
  DataClassBrick val;
  CursorPositionDCHolder cursorPositionDCHolder = ServiceHolder.editorModelCoupler.getCursorPositionDCHolder();

  public static BrickVisurVar make(DataClassBrick val) {
    return new BrickVisurVar(val);
  }

  public BrickVisurVar(DataClassBrick val) {
    this.val = val;
  }

  @Override
  public DataClassBrick getVal() {
    String brickName = val.getName();
    Result<DataClassBrick> r = val.getOrCalculate(brickName, cursorPositionDCHolder);
    return r.getVal();
  }

  @Override
  public void putVal(Object brick) {
    val = (DataClassBrick) brick;
  }

}
