package com.ple.visur;
import static com.ple.visur.Operator.*;

public class LiteralStringWord implements Word {

  public static LiteralStringWord make() {
    return new LiteralStringWord();
  }

  @Override
  public Operator toOperator() {
    return literalStringOperator;
  }

}
