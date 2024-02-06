package com.ple.visur;

public class CommandExecutionService {

  public static CommandExecutionService make() {
    return new CommandExecutionService();
  }

  public void execute(VisurCommand command) {
    for(int i = 0; i < command.ops.size(); i++) {

    }
  }
}
