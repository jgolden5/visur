package com.ple.visur;

import DataClass.DataClassBrick;
import DataClass.PrimitiveDataClassBrick;

public class VisurVar {
  Object obj;
  PrimitiveDataClassBrick brick;

  public VisurVar(Object obj, PrimitiveDataClassBrick brick) {
    this.obj = obj;
    this.brick = brick;
  }

  public static VisurVar make(Object objMaybeBrick) {
    if(objMaybeBrick instanceof PrimitiveDataClassBrick) {
      return new VisurVar(null, (PrimitiveDataClassBrick) objMaybeBrick);
    } else {
      return new VisurVar(objMaybeBrick, null);
    }
  }

  public static VisurVar make(Object obj, PrimitiveDataClassBrick brick) {
    return new VisurVar(obj, brick);
  }
  public Object getObj() {
    return obj;
  }
  public PrimitiveDataClassBrick getBrick() {
    return brick;
  }
  public void putObj(Object obj) {
    this.obj = obj;
  }
  public void putBrick(PrimitiveDataClassBrick brick) {
    this.brick = brick;
  }
}
