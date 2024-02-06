package com.ple.visur;

import static com.ple.visur.Operator.recallOperator;

public class RecallWord implements Word {

  public static RecallWord make() {
    return new RecallWord();
  }

  @Override
  public Operator toOperator() {
    return recallOperator;
  }

}
