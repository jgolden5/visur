package com.ple.visur;

import CursorPositionDC.CursorPositionDCHolder;
import DataClass.CompoundDataClassBrick;
import DataClass.DataClassBrick;
import DataClass.PrimitiveDataClassBrick;
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
  public Integer getVal() {
    String brickName = val.getName();
    Result<DataClassBrick> r = val.getOrCalculate(brickName, cursorPositionDCHolder);
    PrimitiveDataClassBrick dcb = (PrimitiveDataClassBrick) r.getVal();
    return (Integer) dcb.getDFB().getVal();
  }

  @Override
  public void putVal(Object o) {
    
  }

}
