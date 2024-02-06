package com.ple.visur;

import static com.ple.visur.Operator.assignmentWordOperator;

public class AssignmentWord implements Word {

  public static AssignmentWord make() {
    return new AssignmentWord();
  }

  @Override
  public Operator toOperator() {
    return assignmentWordOperator;
  }
}
