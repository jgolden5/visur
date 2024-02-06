package com.ple.visur;

public class LiteralNumberOperator implements Operator {
  public static LiteralNumberOperator make() {
    return new LiteralNumberOperator();
  }

  @Override
  public void execute(Object opInfo) {
    ServiceHolder.editorModelService.putOnExecutionStateStack(opInfo);
  }
}
