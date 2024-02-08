package com.ple.visur;

public class AbsoluteMoveService implements OperatorService {
  public static AbsoluteMoveService make() {
    return new AbsoluteMoveService();
  }

  public void absoluteMove() {
    //do absolute move
  }

  @Override
  public void execute(Operator operator, Object... args) {
    absoluteMove();
  }

}
