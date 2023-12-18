package com.ple.visur;

public class InsertCharService implements OperatorService {
  final EditorModelService ems = ServiceHolder.editorModelService;

  public static InsertCharService make() {
    return new InsertCharService();
  }


  @Override
  public void execute(Operator operator, Object... args) {

  }
}
