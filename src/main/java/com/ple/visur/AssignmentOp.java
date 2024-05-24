package com.ple.visur;

import DataClass.DataClassBrick;
import DataClass.PrimitiveDataClassBrick;

public class AssignmentOp implements Operator {

  public static AssignmentOp make() {
    return new AssignmentOp();
  }

  @Override
  public void execute(Object opInfo) {
    EditorModelCoupler emc = ServiceHolder.editorModelCoupler;
    ExecutionDataStack es = emc.getExecutionDataStack();
    Object topElementFromStack = es.pop();
    VisurVar vv;
    if(topElementFromStack instanceof PrimitiveDataClassBrick) {
      vv = BrickVisurVar.make((PrimitiveDataClassBrick) topElementFromStack);
    } else {
      vv = ObjectVisurVar.make(topElementFromStack);
    }
    emc.putGlobalVar((String)opInfo, vv);
  }

}
