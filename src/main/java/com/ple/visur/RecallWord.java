package com.ple.visur;

public class RecallWord implements Word {

  public static RecallWord make() {
    return new RecallWord();
  }

  @Override
  public Operator toOperator() {
    return RecallOperator.make();
  }

}
