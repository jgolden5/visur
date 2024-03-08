package com.ple.visur;

public class InsertCharService implements OperatorService {
  final EditorModelService ems = ServiceHolder.editorModelService;
  final VisurVar contentXVisurVar = ems.getGlobalVar("ca");
  final VisurVar contentYVisurVar = ems.getGlobalVar("contentY");

  public static InsertCharService make() {
    return new InsertCharService();
  }

  private void insertChar(KeyPressed keyPressed) {

  }

  private void deleteCurrentChar() {

  }

  private void deleteToEndOfPreviousLine() {

  }

  private void insertNewLine() {

  }

  @Override
  public void execute(Operator operator, Object... args) {
//    switch(operator) {
//      case insertChar:
//        insertChar((KeyPressed)args[0]);
//        break;
//      case insertNewLine:
//        insertNewLine();
//        break;
//      case deleteCurrentChar:
//        deleteCurrentChar();
//        break;
//      default:
//        ems.reportError("Operator not recognized");
//    }

  }


}
