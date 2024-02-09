package com.ple.visur;

import java.sql.SQLOutput;

public class NativeOperatorWord implements Word {

  String opSource;

  public NativeOperatorWord(String opSource) {
    this.opSource = opSource;
  }

  @Override
  public CompiledWordResponse compile(String sentence) {
    CompiledWordResponse cwr;
    Operator op;
    switch(opSource) {
      case "absoluteMove":
        op = new AbsoluteMoveOperator();
      default:
        System.out.println("operator " + opSource + " not recognized.");
    }
    return null;
  }
}
