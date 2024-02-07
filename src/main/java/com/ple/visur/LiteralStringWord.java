package com.ple.visur;

public class LiteralStringWord implements Word {

  public static LiteralStringWord make() {
    return new LiteralStringWord();
  }

  @Override
  public Operator toOperator() {
    return LiteralStringOperator.make();
  }

}
