package com.ple.visur;

import java.sql.SQLOutput;

import static com.ple.visur.Operator.nativeOperator;

public class NativeOperatorWord implements Word {

  String opSource;

  public NativeOperatorWord(String opSource) {
    this.opSource = opSource;
  }

  public static NativeOperatorWord make(String opSource) {
    return new NativeOperatorWord(opSource);
  }

  @Override
  public Operator toOperator() {
    Operator op = null;
    switch(opSource) {
      case "absoluteMove":
        op = new AbsoluteMoveOperator();
      default:
        System.out.println("operator " + opSource + " not recognized.");
    }
    return op;
  }
}
