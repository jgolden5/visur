package com.ple.visur;

import static com.ple.visur.Operator.*;

public class LiteralNumberWord implements Word {

  public static Word make() {
    return new LiteralNumberWord();
  }

  @Override
  public Operator toOperator() {
    return literalNumberOperator;
  }

}
