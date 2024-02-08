package com.ple.visur;

public class CompiledWord {
  Operator op;
  Object opInfo;
  String remainingSentence;

  public CompiledWord(Operator op, Object opInfo, String remainingSentence) {
    this.op = op;
    this.opInfo = opInfo;
    this.remainingSentence = remainingSentence;
  }

}
