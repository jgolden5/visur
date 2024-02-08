package com.ple.visur;

public class CompiledWordResponse {
  Operator op;
  Object opInfo;
  String remainingSentence;

  public CompiledWordResponse(Operator op, Object opInfo, String remainingSentence) {
    this.op = op;
    this.opInfo = opInfo;
    this.remainingSentence = remainingSentence;
  }

}
