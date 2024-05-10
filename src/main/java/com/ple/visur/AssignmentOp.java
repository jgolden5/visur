package com.ple.visur;

import DataClass.DataClassBrick;

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
    if(topElementFromStack instanceof DataClassBrick) {
      vv = BrickVisurVar.make((DataClassBrick) topElementFromStack);
    } else {
      vv = ObjectVisurVar.make(topElementFromStack);
    }
    emc.putGlobalVar((String)opInfo, vv);
  }

}
