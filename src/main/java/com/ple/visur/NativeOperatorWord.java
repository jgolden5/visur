package com.ple.visur;

import static com.ple.visur.Operator.nativeOperator;

public class NativeOperatorWord implements Word {

  public static NativeOperatorWord make() {
    return new NativeOperatorWord();
  }

  @Override
  public Operator toOperator() {
    return nativeOperator;
  }
}
