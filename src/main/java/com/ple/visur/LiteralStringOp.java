package com.ple.visur;

public class LiteralStringOp implements Operator {

  public static LiteralStringOp make() {
    return new LiteralStringOp();
  }

  @Override
  public void execute(Object opInfo) {
    ServiceHolder.editorModelService.putOnExecutionDataStack(opInfo);
  }

}
