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

  private void deleteCurrentChar() {
    String currentLine = ems.getCurrentContentLine();
    int contentX = ems.getContentX();
    if(contentX > 0) {
      String substrBeforeDeletedChar = currentLine.substring(0, contentX - 1);
      String substrAfterDeletedChar = currentLine.substring(contentX, ems.getCurrentContentLineLength());
      currentLine = substrBeforeDeletedChar + substrAfterDeletedChar;
      String[] editorContentLines = ems.getEditorContentLines();
      editorContentLines[ems.getContentY()] = currentLine;
      ems.putEditorContentLines(editorContentLines);
      ServiceHolder.cursorMovementService.cursorLeft();
    } else {
      if(ems.getContentY() > 0) {
        deleteToEndOfPreviousLine();
      }
    }
  }

  private void deleteToEndOfPreviousLine() {
    String[] oldContentLines = ems.getEditorContentLines();
    String[] newContentLines = new String[oldContentLines.length - 1];
    int contentY = ems.getContentY();
    int newCursorX = oldContentLines[contentY - 1].length();
    for(int i = 0; i < newContentLines.length; i++) {
      if(i < contentY - 1) {
        newContentLines[i] = oldContentLines[i];
      } else if(i == contentY - 1) {
        newContentLines[i] = oldContentLines[contentY - 1] + oldContentLines[contentY];
      } else {
        newContentLines[i] = oldContentLines[i + 1];
      }
    }
    ems.putContentX(newCursorX);
    ems.putVirtualX(newCursorX);
    ems.putContentY(contentY - 1);
    ems.putEditorContentLines(newContentLines);
  }

  private void insertNewLine() {
    int highestYAllowed = ems.getEditorContentLines().length - 1;
    if(highestYAllowed < ems.getCanvasHeight() - 2) {
      int contentX = ems.getContentX();
      int contentY = ems.getContentY();
      String currentLine = ems.getCurrentContentLine();
      String substrBeforeNewLine = currentLine.substring(0, contentX);
      String substrAtNewLine = currentLine.substring(contentX, ems.getCurrentContentLineLength());
      String[] oldEditorContentLines = ems.getEditorContentLines();
      String[] newEditorContentLines = new String[ems.getEditorContentLines().length + 1];
      for (int i = 0; i < oldEditorContentLines.length; i++) {
        if (i < contentY) {
          newEditorContentLines[i] = oldEditorContentLines[i];
        } else if (i == contentY) {
          newEditorContentLines[i] = substrBeforeNewLine;
        } else {
          newEditorContentLines[i + 1] = oldEditorContentLines[i];
        }
      }
      contentY++;
      newEditorContentLines[contentY] = substrAtNewLine;
      ems.putContentX(0);
      ems.putVirtualX(0);
      ems.putContentY(contentY);
      ems.putEditorContentLines(newEditorContentLines);
    }
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
        deleteCurrentChar();
        break;
      default:
        ems.reportError("Operator not recognized");
    }

  }


}
