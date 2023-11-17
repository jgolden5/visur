package com.ple.visur;

import io.vertx.core.Future;
import io.vertx.core.json.JsonObject;
import io.vertx.rxjava3.core.eventbus.Message;

public class KeyWasPressedVerticle extends AbstractVisurVerticle {
  int lineStartY = 0;

  @Override
  public void start() {
    vertx.eventBus().consumer(BusEvent.keyWasPressed.name(), this::handle);
  }

  public void handle(Message event) {
    JsonObject keyJson = new JsonObject((String) event.body());
    final String key = keyJson.getString("key");
    mapKeys(key);
    boolean modelChanged = true;
    if (modelChanged) {
      bus.send(BusEvent.modelChange.name(), null);
    }
  }

  public Future<Void> keyPress(String key) {
    return Future.succeededFuture();
  }

  public void mapKeys(String key) {
    int contentX = editorModelService.getContentX();
    int virtualX = editorModelService.getVirtualX();
    int contentY = editorModelService.getContentY();
    final int canvasWidth = editorModelService.getCanvasWidth();
    final int canvasHeight = editorModelService.getCanvasHeight();
    int canvasX = editorModelService.getCanvasX();
    int canvasY = editorModelService.getCanvasY();
    String[] contentLines = editorModelService.getContentLines();
    String currentContentLine = contentLines[contentY];
    int currentContentLineLength = currentContentLine.length();
    int numberOfRowsInCurrentContentLine = currentContentLineLength / canvasWidth;
    if(currentContentLineLength % canvasWidth != 0) {
      numberOfRowsInCurrentContentLine++;
    }

    if (key.equals("h")) {
      if(contentX >= currentContentLineLength) {
        contentX = currentContentLineLength - 1;
      }
      if(contentX > 0) {
        contentX--;
        editorModelService.putContentX(contentX);
        editorModelService.putVirtualX(contentX);
      }
    } else if (key.equals("l")) {
      if(contentX >= currentContentLineLength) {
        contentX = currentContentLineLength - 1;
      }
      if(contentX < currentContentLineLength - 1) {
        contentX++;
        editorModelService.putContentX(contentX);
        editorModelService.putVirtualX(contentX);
      }
    } else if (key.equals("j")) {
      if(contentY < contentLines.length - 1) {
        String nextLine = contentLines[contentY + 1];
        int nextLineLength = nextLine.length();
        contentY++;
        if(nextLineLength - 1 >= virtualX) {
          contentX = virtualX;
        } else {
          contentX = nextLineLength - 1;
        }
        assignCursorCoordinates(contentX, contentY);
      }
    } else if (key.equals("k")) {
      if(contentY > 0) {
        String previousLine = contentLines[contentY - 1];
        int previousLineLength = previousLine.length();
        contentY--;
        if(previousLineLength - 1 >= virtualX) {
          contentX = virtualX;
        } else {
          contentX = previousLineLength - 1;
        }
        assignCursorCoordinates(contentX, contentY);
      }
//    } else if (key.equals("w") || key.equals("W")) {
//      int startingPositionInContentLine = canvasWidth * (contentY - lineStartY) + contentX;
//      int cursorDestinationIndex = wFindCursorDestinationIndex(startingPositionInContentLine, key, true);
//
//      assignCursorCoordinates(cursorDestinationIndex);
//
//    } else if (key.equals("b") || key.equals("B")) {
//      int startingPositionInContentLine = canvasWidth * (contentY - lineStartY) + contentX;
//      int cursorDestinationIndex = bFindCursorDestinationIndex(startingPositionInContentLine, key, true);
//
//      assignCursorCoordinates(cursorDestinationIndex);
//
    } else if(key.equals("0")) {
      editorModelService.putContentX(0);
      editorModelService.putVirtualX(0);
//    } else if(key.equals("$")) {
//      contentX = lineEndX;
//      contentY = lineEndY;
//      int cursorDestinationIndex = (contentY - lineStartY) * canvasWidth + contentX;
//      assignCursorCoordinates(cursorDestinationIndex);
//      editorModelService.putInterlinearX(1000);
//      editorModelService.putInterlinearY(1000);
//    } else if(key.equals("^")) {
//      int cursorDestination = 0;
//      for(int i = 0; i < canvasWidth; i++) {
//        if(currentContentLine.charAt(i) != ' ' && currentContentLine.charAt(i) != '\t') {
//          cursorDestination = i;
//          break;
//        }
//      }
//      assignCursorCoordinates(cursorDestination);
    }

}

//  public int wFindCursorDestinationIndex(int startingPositionInContentLine, String key, boolean firstIteration) {
//    int cursorDestinationIndex = -1;
//    String specialCharacters = ".,!?:;\"\'";
//    int currentLineNumber = editorModelService.getCurrentLineNumber();
//    String currentLine = dataModelService.getContentLines()[currentLineNumber];
//    int currentLineLength = currentLine.length();
//    char startingChar = currentLine.charAt(startingPositionInContentLine);
//    String startingCharAsString = String.valueOf(startingChar);
//    boolean startedOnSpecialChar = specialCharacters.contains(startingCharAsString);
//    if(startingPositionInContentLine == 0) {
//      startingPositionInContentLine++;
//    }
//
//    if(!firstIteration) {
//      cursorDestinationIndex = 0;
//    } else if(currentLineLength > 1) {
//      for (int i = startingPositionInContentLine; i <= currentLineLength - 1; i++) {
//        char currentChar = currentLine.charAt(i);
//        String currentCharAsString = String.valueOf(currentChar);
//        char previousChar = currentLine.charAt(i - 1);
//        String previousCharAsString = String.valueOf(previousChar);
//
//        boolean currentCharIsSpecialAndShouldBeDestination =
//          specialCharacters.contains(currentCharAsString) &&
//            !specialCharacters.contains(previousCharAsString) &&
//            i != startingPositionInContentLine && key.equals("w");
//        boolean currentCharIsRegularNonspaceAndRightAfterSpecial =
//          !specialCharacters.contains(currentCharAsString) && key.equals("w") && currentChar != ' ';
//
//        if (currentChar != ' ' && previousChar == ' ' && i != startingPositionInContentLine) {
//          cursorDestinationIndex = i;
//        } else if (currentCharIsSpecialAndShouldBeDestination) {
//          cursorDestinationIndex = i;
//        } else if(startedOnSpecialChar) {
//          if(currentCharIsRegularNonspaceAndRightAfterSpecial) {
//            cursorDestinationIndex = i;
//          }
//        }
//        if (cursorDestinationIndex > -1) {
//          break;
//        }
//      }
//      if(cursorDestinationIndex == -1) {
//        if(currentLineNumber < dataModelService.getContentLines().length - 1) {
//          int nextLineNumber = currentLineNumber + 1;
//          editorModelService.putCurrentLineNumber(nextLineNumber);
//          String nextLine = dataModelService.getContentLines()[nextLineNumber];
//          int nextLineLength = nextLine.length();
//          int canvasWidth = editorModelService.getCanvasWidth();
//          int numberOfRowsInCurrentLine = currentLineLength / canvasWidth;
//          if (nextLineLength % canvasWidth != 0) {
//            numberOfRowsInCurrentLine++;
//          }
//          int lineEndY = lineStartY + numberOfRowsInCurrentLine - 1;
//          lineStartY = lineEndY + 1;
//          cursorDestinationIndex = wFindCursorDestinationIndex(0, key, false);
//        } else {
//          cursorDestinationIndex = currentLineLength - 1;
//        }
//      }
//    } else {
//      cursorDestinationIndex = 0;
//    }
//    return cursorDestinationIndex;
//  }

//  public int bFindCursorDestinationIndex(int startingPositionInContentLine, String key, boolean firstIteration) {
//    int cursorDestinationIndex = -1;
//    String specialCharacters = ".,!?:;\"\'";
//    boolean spaceOrSpecialFound = key.equals("b") && !firstIteration;
//    int currentLineNumber = editorModelService.getCurrentLineNumber();
//    String currentLine = dataModelService.getContentLines()[currentLineNumber];
//    if(currentLine.charAt(startingPositionInContentLine) == ' ') {
//      spaceOrSpecialFound = true;
//    }
//
//    for (int i = startingPositionInContentLine; i > 0; i--) {
//      char currentChar = currentLine.charAt(i);
//      String currentCharAsString = String.valueOf(currentChar);
//      char previousChar = currentLine.charAt(i - 1);
//      String previousCharAsString = String.valueOf(previousChar);
//
//      boolean spaceOrSpecialDestinationTargettedToBeFound =
//        previousChar == ' ' && currentChar != ' ' ||
//          specialCharacters.contains(currentCharAsString) && !specialCharacters.contains(previousCharAsString);
//      boolean atBeginningOfWordAndDestination =
//        previousChar == ' ' && currentChar != ' ' && (i != startingPositionInContentLine || !firstIteration);
//      boolean previousCharWasSpecialCurrentIsNotAndLowercaseWasPressed =
//        specialCharacters.contains(previousCharAsString) &&
//          !specialCharacters.contains(currentCharAsString) &&
//          key.equals("b") && spaceOrSpecialFound && currentChar != ' ';
//
//      if (spaceOrSpecialDestinationTargettedToBeFound) {
//        if (!spaceOrSpecialFound) {
//          if (specialCharacters.contains(currentCharAsString) &&
//            !specialCharacters.contains(previousCharAsString) &&
//            i != startingPositionInContentLine && key.equals("b")) {
//            cursorDestinationIndex = i;
//          } else if (atBeginningOfWordAndDestination) {
//            cursorDestinationIndex = i;
//          } else {
//            spaceOrSpecialFound = true;
//          }
//        } else {
//          if (!(key.equals("B") && specialCharacters.contains(currentCharAsString))) {
//            cursorDestinationIndex = i;
//          }
//        }
//      } else if (previousCharWasSpecialCurrentIsNotAndLowercaseWasPressed) {
//        cursorDestinationIndex = i;
//      }
//      if (cursorDestinationIndex > -1) {
//        break;
//      }
//    }
//    if(cursorDestinationIndex == -1) {
//      if(currentLineNumber > 0 && !spaceOrSpecialFound && startingPositionInContentLine == 0) {
//        int previousLineNumber = currentLineNumber - 1;
//        editorModelService.putCurrentLineNumber(previousLineNumber);
//        String previousLine = dataModelService.getContentLines()[previousLineNumber];
//        int previousLineLength = previousLine.length();
//        int canvasWidth = editorModelService.getCanvasWidth();
//        int numberOfRowsInPreviousLine = previousLineLength / canvasWidth;
//        if (previousLineLength % canvasWidth != 0) {
//          numberOfRowsInPreviousLine++;
//        }
//        lineStartY = lineStartY - numberOfRowsInPreviousLine;
//        cursorDestinationIndex = bFindCursorDestinationIndex(previousLineLength - 1, key, false);
//      } else {
//        cursorDestinationIndex = 0;
//      }
//    }
//    return cursorDestinationIndex;
//  }

  public void assignCursorCoordinates(int contentX, int contentY) {
    editorModelService.putContentX(contentX);
    editorModelService.putContentY(contentY);
  }

}
