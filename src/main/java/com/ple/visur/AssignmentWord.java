package com.ple.visur;

public class AssignmentWord implements Word {

  public static AssignmentWord make() {
    return new AssignmentWord();
  }

  @Override
  public Operator toOperator() {
    return AssignmentOperator.make();
  }
}
