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
    ServiceHolder.cursorMovementService.cursorRight();
  }

  private void insertNewLine() {
    String currentLine = ems.getCurrentContentLine();
    int contentX = ems.getContentX();
    int contentY = ems.getContentY();
    String substrBeforeNewLine = currentLine.substring(0, contentX);
    String substrAtNewLine = currentLine.substring(contentX, ems.getCurrentContentLineLength());
    String[] oldEditorContentLines = ems.getEditorContentLines();
    String[] newEditorContentLines = new String[ems.getEditorContentLines().length + 1];
    for(int i = 0; i < oldEditorContentLines.length; i++) {
      if(i < contentY) {
        newEditorContentLines[i] = oldEditorContentLines[i];
      } else if(i == contentY) {
        newEditorContentLines[i] = substrBeforeNewLine;
      } else {
        newEditorContentLines[i + 1] = oldEditorContentLines[i];
      }
    }
    contentY++;
    newEditorContentLines[contentY] = substrAtNewLine;
    ems.putContentX(0);
    ems.putContentY(contentY);
    ems.putEditorContentLines(newEditorContentLines);
  }

  @Override
  public void execute(Operator operator, Object... args) {
    switch(operator) {
      case insertChar:
        insertChar((KeyPressed)args[0]);
        break;
      case insertNewLine:
        insertNewLine();
        break;
      case deleteCurrentChar:
//        deleteCurrentChar();
        break;
      default:
        ems.reportError("Operator not recognized");
    }

  }


}
