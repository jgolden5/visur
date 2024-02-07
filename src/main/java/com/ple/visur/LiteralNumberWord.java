package com.ple.visur;

public class LiteralNumberWord implements Word {

  public static LiteralNumberWord make() {
    return new LiteralNumberWord();
  }

  @Override
  public Operator toOperator() {
    return LiteralNumberOperator.make();
  }

}
