package com.ple.visur;

public class AssignmentWord implements Word {

  @Override
  public Operator toOperator() {
    return AssignmentOperator.make();
  }
}
