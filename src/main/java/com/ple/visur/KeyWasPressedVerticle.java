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
    int contentX = editorModelService.getContentLineX();
    int contentY = editorModelService.getContentLineY();
    final int canvasWidth = editorModelService.getCanvasWidth();
    final int canvasHeight = editorModelService.getCanvasHeight();
    int canvasX = editorModelService.getCanvasX();
    int canvasY = editorModelService.getCanvasY();
    String currentContentLine = dataModelService.getContentLines()[contentY];
    int currentLineLength = currentContentLine.length();
    int numberOfRowsInCurrentContentLine = currentLineLength / canvasWidth;
    if(currentLineLength % canvasWidth != 0) {
      numberOfRowsInCurrentContentLine++;
    }

    if (key.equals("h")) {
      if(contentX > 0) {
        contentX--;
        editorModelService.putContentLineX(contentX);
      }
    } else if (key.equals("l")) {
      if(contentX < currentLineLength - 1) {
        contentX++;
        editorModelService.putContentLineX(contentX);
      }
//    } else if (key.equals("j")) {
//      String nextLine = currentLineNumber + 1 == dataModelService.getContentLines().length ?
//        "" : dataModelService.getContentLines()[currentLineNumber + 1];
//      int nextLineLength = nextLine.length();
//
//      if (contentY < canvasHeight - 1) {
//        boolean shouldGoDown;
//        int numberOfRowsInNextLine = nextLineLength / canvasWidth + 1;
//        int nextLineStartY = nextLineLength > 0 ? lineEndY + 1 : -1;
//        int nextLineEndX = nextLineLength % canvasWidth - 1;
//        int nextLineEndY = numberOfRowsInNextLine - 1 + nextLineStartY;
//        if (nextLineEndX == -1) {
//          nextLineEndX = canvasWidth - 1;
//        }
//        shouldGoDown = currentLineNumber < dataModelService.getContentLines().length - 1;
//        if (shouldGoDown) {
//          boolean interlinearYTooBig = interlinearY + nextLineStartY > nextLineEndY;
//          if (interlinearYTooBig) {
//            contentY = nextLineEndY;
//          } else {
//            contentY = interlinearY + nextLineStartY;
//          }
//          if (nextLineLength > 0) {
//            lineStartY = nextLineStartY;
//          }
//          boolean shouldGoToEndOfNextLine = interlinearY + nextLineStartY >= nextLineEndY;
//          boolean interlinearXTooBig;
//          if (shouldGoToEndOfNextLine) {
//            interlinearXTooBig = interlinearX > nextLineEndX;
//          } else {
//            interlinearXTooBig = false;
//          }
//          if (shouldGoToEndOfNextLine && interlinearXTooBig || interlinearYTooBig) {
//            contentX = nextLineEndX;
//          } else {
//            contentX = interlinearX;
//          }
//          editorModelService.putCurrentLineNumber(currentLineNumber + 1);
//        }
//      }
//      editorModelService.putCursorX(contentX);
//      editorModelService.putCursorY(contentY);
//
//    } else if (key.equals("k")) {
//      String previousLine = currentLineNumber - 1 < 0 ?
//        "" : dataModelService.getContentLines()[currentLineNumber - 1];
//      int previousLineLength = previousLine.length();
//
//      if (currentLineNumber > 0) {
//        boolean shouldGoUp;
//        int numberOfRowsInPreviousLine = previousLineLength / canvasWidth;
//        if (previousLineLength % canvasWidth != 0) {
//          numberOfRowsInPreviousLine++; //for the remainder
//        }
//        int previousLineStartY = lineStartY - numberOfRowsInPreviousLine;
//        int previousLineEndX = 0;
//        int previousLineEndY = previousLineLength > 0 ? lineStartY - 1 : 0;
//        if (currentLineNumber > 0) {
//          previousLineEndX = previousLineLength % canvasWidth - 1;
//          if (previousLineLength % canvasWidth == 0) {
//            previousLineEndX = canvasWidth - 1;
//          }
//        }
//        shouldGoUp = lineStartY > 0;
//        if (shouldGoUp) {
//          boolean interlinearYTooBig = interlinearY + previousLineStartY > previousLineEndY;
//          if (interlinearYTooBig) {
//            contentY = previousLineEndY;
//          } else {
//            contentY = interlinearY + previousLineStartY;
//          }
//          if (previousLineLength > 0) {
//            lineStartY = previousLineStartY;
//          }
//          boolean shouldGoToEndOfPreviousLine = interlinearY + previousLineStartY >= previousLineEndY;
//          boolean interlinearXTooBig;
//          if (shouldGoToEndOfPreviousLine) {
//            interlinearXTooBig = interlinearX > previousLineEndX;
//          } else {
//            interlinearXTooBig = false;
//          }
//          if (shouldGoToEndOfPreviousLine && interlinearXTooBig || interlinearYTooBig) {
//            contentX = previousLineEndX;
//          } else {
//            contentX = interlinearX;
//          }
//        }
//        editorModelService.putCurrentLineNumber(currentLineNumber - 1);
//      }
//      editorModelService.putCursorX(contentX);
//      editorModelService.putCursorY(contentY);
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
//    } else if(key.equals("0")) {
//      assignCursorCoordinates(0);
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
    editorModelService.putContentLineX(contentX);
    editorModelService.putContentLineY(contentY);
  }

//  public void assignCursorCoordinates(int cursorDestinationIndex) {
//    int canvasWidth = editorModelService.getCanvasWidth();
//    int interlinearY = cursorDestinationIndex / canvasWidth;
//    int x = cursorDestinationIndex % canvasWidth;
//    int y = lineStartY + interlinearY;
//
//    editorModelService.putCursorX(x);
//    editorModelService.putCursorY(y);
//    editorModelService.putInterlinearX(x);
//    editorModelService.putInterlinearY(interlinearY);
//
//  }
}
