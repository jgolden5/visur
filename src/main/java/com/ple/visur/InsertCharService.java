package com.ple.visur;

import java.security.Provider;

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
        ServiceHolder.cursorMovementService.cursorRight();
        break;
      case insertEmptyLineBelowCurrentLine:
//        insertEmptyLineBelowCurrentLine();
        break;
      case deleteCurrentChar:
//        deleteCurrentChar();
        break;
      default:
        ems.reportError("Operator not recognized");
    }

  }

}
