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
      switch(currentOp.getClass().getSimpleName()) {
        case "LiteralNumberOperator":
          LiteralNumberOperator.make().execute(currentOpInfo);
          break;
        case "LiteralStringOperator":
          LiteralStringOperator.make().execute(currentOpInfo);
          break;
      }
    }
  }
}
