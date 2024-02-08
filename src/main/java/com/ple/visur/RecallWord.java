package com.ple.visur;

public class RecallWord implements Word {

  @Override
  public Operator toOperator() {
    return RecallOperator.make();
  }

}
