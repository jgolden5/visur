package com.ple.visur;

import java.sql.SQLOutput;

public class NativeOperatorWord implements Word {

  String opSource;

  public NativeOperatorWord(String opSource) {
    this.opSource = opSource;
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
