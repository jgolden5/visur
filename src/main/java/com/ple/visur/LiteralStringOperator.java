package com.ple.visur;

public class LiteralStringOperator implements Operator {

  public static LiteralStringOperator make() {
    return new LiteralStringOperator();
  }

  @Override
  public void execute(Object opInfo) {
    ServiceHolder.editorModelService.putOnExecutionDataStack(opInfo);
  }

}
