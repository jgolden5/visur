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
    String nameToPut = (String)es.pop();
    String nameOfValueToRetrieve = (String)es.pop();
    Object valueRetrieved = emc.getGlobalVar(nameOfValueToRetrieve);
    VisurVar vv;
    if(valueRetrieved instanceof PrimitiveDataClassBrick) {
      vv = BrickVisurVar.make((PrimitiveDataClassBrick) valueRetrieved);
    } else {
      vv = ObjectVisurVar.make(valueRetrieved);
    }
    emc.putGlobalVar(nameToPut, vv);
  }

}
