package com.ple.visur;

import CursorPositionDC.CursorPositionDCHolder;
import DataClass.DataClassBrick;
import DataClass.PrimitiveDataClassBrick;
import DataClass.Result;

public class BrickVisurVar implements VisurVar {
  DataClassBrick val;
  static CursorPositionDCHolder cursorPositionDCHolder = ServiceHolder.editorModelCoupler.getCursorPositionDCHolder();

  public static BrickVisurVar make(DataClassBrick val) {
    return new BrickVisurVar(val);
  }

  public static BrickVisurVar make(String name, int val) {
//    DataClassBrick brick = null;
//    return new BrickVisurVar(cursorPositionDCHolder.wholeNumberDC.makeBrick(val));
    return null;
  }

  public BrickVisurVar(DataClassBrick val) {
    this.val = val;
  }

  @Override
  public Integer getVal() {
    Result<DataClassBrick> r = Result.make();
    if(val instanceof PrimitiveDataClassBrick) {
      r = ((PrimitiveDataClassBrick)val).getOrCalc();
    }
    PrimitiveDataClassBrick dcb = (PrimitiveDataClassBrick) r.getVal();
    return (Integer) dcb.getDFB().getVal();
  }

  @Override
  public void putVal(Object o) {

  }

}
