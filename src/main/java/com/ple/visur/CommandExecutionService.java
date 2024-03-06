package com.ple.visur;

import java.util.ArrayList;

public class CommandExecutionService {

  public static CommandExecutionService make() {
    return new CommandExecutionService();
  }

  public void execute(VisurCommand command) {
    ArrayList<Operator> ops = command.ops;
    for(int i = 0; i < command.ops.size(); i++) {
      Operator currentOp = ops.get(i);
      Object currentOpInfo = command.opInfo.get(i);
      String operatorInstanceName = currentOp.getClass().getSimpleName();
      switch(operatorInstanceName) {
        case "LiteralNumberOp":
          LiteralNumberOp.make().execute(currentOpInfo);
          break;
        case "LiteralStringOp":
          LiteralStringOp.make().execute(currentOpInfo);
          break;
        case "AssignmentOp":
          AssignmentOp.make().execute(currentOpInfo);
          break;
        case "NativeOperator":
          //get and execute operator based on opInfo. We want to work on this one when we can test it
          break;
        case "RecallOp":
          RecallOp.make().execute(currentOpInfo);
          break;
        default:
          currentOp.execute(currentOpInfo); //should be null
      }
    }
  }
}
