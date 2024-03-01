package com.ple.visur;

public class InsertCharService implements OperatorService {
  final EditorModelService ems = ServiceHolder.editorModelService;
  final VisurVar contentXVisurVar = ems.getGlobalVar("contentX");
  final VisurVar contentYVisurVar = ems.getGlobalVar("contentY");

  public static InsertCharService make() {
    return new InsertCharService();
  }

  private void insertChar(KeyPressed keyPressed) {
    String currentLine = ems.getLineAtY(i);
    int contentX = contentXVisurVar.getInt();
    char charToInsert = keyPressed.getKey().charAt(0);
    String substrBeforeInsertedChar = currentLine.substring(0, contentX);
    String substrAfterInsertedChar = currentLine.substring(contentX, ems.getCurrentContentLineLength());
    currentLine = substrBeforeInsertedChar + charToInsert + substrAfterInsertedChar;
    String[] editorContentLines = ems.getEditorContent();
    editorContentLines[contentYVisurVar.getInt()] = currentLine;
    ems.putEditorContent(editorContentLines);
    ServiceHolder.cursorMovementService.cursorRight();
  }

  private void deleteCurrentChar() {
    String currentLine = ems.getLineAtY(i);
    int contentX = contentXVisurVar.getInt();
    int contentY = contentYVisurVar.getInt();
    if(contentX > 0) {
      String substrBeforeDeletedChar = currentLine.substring(0, contentX - 1);
      String substrAfterDeletedChar = currentLine.substring(contentX, ems.getCurrentContentLineLength());
      currentLine = substrBeforeDeletedChar + substrAfterDeletedChar;
      String[] editorContentLines = ems.getEditorContent();
      editorContentLines[contentY] = currentLine;
      ems.putEditorContent(editorContentLines);
      ServiceHolder.cursorMovementService.cursorLeft();
    } else {
      if(contentY > 0) {
        deleteToEndOfPreviousLine();
      }
    }
  }

  private void deleteToEndOfPreviousLine() {
    String[] oldContentLines = ems.getEditorContent();
    String[] newContentLines = new String[oldContentLines.length - 1];
    int contentY = contentYVisurVar.getInt();
    int newContentX = oldContentLines[contentY - 1].length();
    for(int i = 0; i < newContentLines.length; i++) {
      if(i < contentY - 1) {
        newContentLines[i] = oldContentLines[i];
      } else if(i == contentY - 1) {
        newContentLines[i] = oldContentLines[contentY - 1] + oldContentLines[contentY];
      } else {
        newContentLines[i] = oldContentLines[i + 1];
      }
    }
    contentXVisurVar.put(newContentX);
    ems.putVirtualX(newContentX);
    contentYVisurVar.put(contentY - 1);
    ems.putEditorContent(newContentLines);
  }

  private void insertNewLine() {
    int maxYBeforeFooter = ems.getEditorContent().length - 1;
    if(maxYBeforeFooter < ems.getCanvasHeight() - 2) {
      int contentX = contentXVisurVar.getInt();
      int contentY = contentYVisurVar.getInt();
      String currentLine = ems.getLineAtY(i);
      String substrBeforeNewLine = currentLine.substring(0, contentX);
      String substrAtNewLine = currentLine.substring(contentX, ems.getCurrentContentLineLength());
      String[] oldEditorContentLines = ems.getEditorContent();
      String[] newEditorContentLines = new String[ems.getEditorContent().length + 1];
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
      contentXVisurVar.put(0);
      ems.putVirtualX(0);
      contentYVisurVar.put(contentY);
      ems.putEditorContent(newEditorContentLines);
    }
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
