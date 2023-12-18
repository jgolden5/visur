package com.ple.visur;

public class InsertCharService implements OperatorService {
  final EditorModelService ems = ServiceHolder.editorModelService;

  public static InsertCharService make() {
    return new InsertCharService();
  }

  private void insertChar(KeyPressed keyPressed) {
    String currentLine = ems.getCurrentContentLine();
    int contentX = ems.getContentX();
    char charToInsert = keyPressed.getKey().charAt(0);
    String substrBeforeInsertedChar = currentLine.substring(0, contentX);
    String substrAfterInsertedChar = currentLine.substring(contentX, ems.getCurrentContentLineLength());
    currentLine = substrBeforeInsertedChar + charToInsert + substrAfterInsertedChar;
    String[] editorContentLines = ems.getEditorContentLines();
    editorContentLines[ems.getContentY()] = currentLine;
    ems.putEditorContentLines(editorContentLines);
  }

  @Override
  public void execute(Operator operator, Object... args) {
    switch(operator) {
      case insertChar:
        insertChar((KeyPressed)args[0]);
        ems.putContentX(ems.getContentX() + 1);
        ems.putVirtualX(ems.getContentX());
        break;
      case insertEmptyLineBelowCurrentLine:
//        insertEmptyLineBelowCurrentLine();
        break;
      case deleteCurrentChar:
//        deleteCurrentChar();
        break;
      default:
        ems.reportError("Operator not recognzied");
    }

  }

}
