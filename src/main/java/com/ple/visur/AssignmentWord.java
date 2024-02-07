package com.ple.visur;

import static com.ple.visur.Operator.assignmentOperator;

public class AssignmentWord implements Word {

  public static AssignmentWord make() {
    return new AssignmentWord();
  }

  @Override
  public Operator toOperator() {
    return AssignmentOperator.make();
  }
}
