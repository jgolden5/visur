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
    int x = editorModelService.getCursorX();
    int y = editorModelService.getCursorY();
    int currentLineNumber = editorModelService.getCurrentLineNumber();
    String currentLine = dataModelService.getContentLines()[currentLineNumber];
    int currentLineLength = currentLine.length();
    final int canvasWidth = editorModelService.getCanvasWidth();
    final int canvasHeight = editorModelService.getCanvasHeight();
    int numberOfRowsInCurrentLine = currentLineLength / canvasWidth;
    int lineEndY = lineStartY + numberOfRowsInCurrentLine;
    if (currentLineLength % canvasWidth == 0) {
      lineEndY--;
    }
    int lineEndX = currentLineLength % canvasWidth - 1;
    if (lineEndX == -1) {
      lineEndX = canvasWidth - 1; //weird workaround
    }
    int interlinearX = editorModelService.getInterlinearX();
    int interlinearY = editorModelService.getInterlinearY();

    if (key.equals("h")) {
      boolean shouldGoUp = y > lineStartY && x == 0;
      boolean shouldGoLeft = !shouldGoUp && x > 0;
      if (shouldGoLeft) {
        x--;
      } else if (shouldGoUp) {
        y--;
        x = canvasWidth - 1;
        editorModelService.putCursorY(y);
      }
      editorModelService.putCursorX(x);
      editorModelService.putInterlinearX(x);
      editorModelService.putInterlinearY(y - lineStartY);
    } else if (key.equals("j")) {
      String nextLine = currentLineNumber + 1 == dataModelService.getContentLines().length ?
        "" : dataModelService.getContentLines()[currentLineNumber + 1];
      int nextLineLength = nextLine.length();

      if (y < canvasHeight - 1) {
        boolean shouldGoDown;
        int numberOfRowsInNextLine = nextLineLength / canvasWidth + 1;
        int nextLineStartY = nextLineLength > 0 ? lineEndY + 1 : -1;
        int nextLineEndX = nextLineLength % canvasWidth - 1;
        int nextLineEndY = numberOfRowsInNextLine - 1 + nextLineStartY;
        if (nextLineEndX == -1) {
          nextLineEndX = canvasWidth - 1;
        }
        shouldGoDown = currentLineNumber < dataModelService.getContentLines().length - 1;
        if (shouldGoDown) {
          boolean interlinearYTooBig = interlinearY + nextLineStartY > nextLineEndY;
          if (interlinearYTooBig) {
            y = nextLineEndY;
          } else {
            y = interlinearY + nextLineStartY;
          }
          if (nextLineLength > 0) {
            lineStartY = nextLineStartY;
          }
          boolean shouldGoToEndOfNextLine = interlinearY + nextLineStartY >= nextLineEndY;
          boolean interlinearXTooBig;
          if (shouldGoToEndOfNextLine) {
            interlinearXTooBig = interlinearX > nextLineEndX;
          } else {
            interlinearXTooBig = false;
          }
          if (shouldGoToEndOfNextLine && interlinearXTooBig || interlinearYTooBig) {
            x = nextLineEndX;
          } else {
            x = interlinearX;
          }
          editorModelService.putCurrentLineNumber(currentLineNumber + 1);
        }
      }
      editorModelService.putCursorX(x);
      editorModelService.putCursorY(y);

    } else if (key.equals("k")) {
      String previousLine = currentLineNumber - 1 < 0 ?
        "" : dataModelService.getContentLines()[currentLineNumber - 1];
      int previousLineLength = previousLine.length();

      if (currentLineNumber > 0) {
        boolean shouldGoUp;
        int numberOfRowsInPreviousLine = previousLineLength / canvasWidth;
        if (previousLineLength % canvasWidth != 0) {
          numberOfRowsInPreviousLine++; //for the remainder
        }
        int previousLineStartY = lineStartY - numberOfRowsInPreviousLine;
        int previousLineEndX = 0;
        int previousLineEndY = previousLineLength > 0 ? lineStartY - 1 : 0;
        if (currentLineNumber > 0) {
          previousLineEndX = previousLineLength % canvasWidth - 1;
          if (previousLineLength % canvasWidth == 0) {
            previousLineEndX = canvasWidth - 1;
          }
        }
        shouldGoUp = lineStartY > 0;
        if (shouldGoUp) {
          boolean interlinearYTooBig = interlinearY + previousLineStartY > previousLineEndY;
          if (interlinearYTooBig) {
            y = previousLineEndY;
          } else {
            y = interlinearY + previousLineStartY;
          }
          if (previousLineLength > 0) {
            lineStartY = previousLineStartY;
          }
          boolean shouldGoToEndOfPreviousLine = interlinearY + previousLineStartY >= previousLineEndY;
          boolean interlinearXTooBig;
          if (shouldGoToEndOfPreviousLine) {
            interlinearXTooBig = interlinearX > previousLineEndX;
          } else {
            interlinearXTooBig = false;
          }
          if (shouldGoToEndOfPreviousLine && interlinearXTooBig || interlinearYTooBig) {
            x = previousLineEndX;
          } else {
            x = interlinearX;
          }
        }
        editorModelService.putCurrentLineNumber(currentLineNumber - 1);
      }
      editorModelService.putCursorX(x);
      editorModelService.putCursorY(y);
    } else if (key.equals("l")) {
      boolean shouldGoRight;
      boolean shouldGoDown;
      if (y < lineEndY) {
        if (x < canvasWidth - 1) {
          shouldGoRight = true;
          shouldGoDown = false;
        } else {
          shouldGoRight = false;
          shouldGoDown = true;
        }
      } else {
        shouldGoRight = x < lineEndX;
        shouldGoDown = false;
      }

      if (shouldGoRight) {
        x++;
      } else if (shouldGoDown) {
        y++;
        x = 0;
        editorModelService.putCursorY(y);
      }
      editorModelService.putCursorX(x);
      editorModelService.putInterlinearX(x);
      if (shouldGoDown) {
        editorModelService.putInterlinearY(y - lineStartY);
      }
    } else if (key.equals("w") || key.equals("W")) {
      int startingPositionInContentLine = canvasWidth * (y - lineStartY) + x;
      int cursorDestinationIndex = wFindCursorDestinationIndex(startingPositionInContentLine, key, true);

      assignCursorCoordinates(cursorDestinationIndex);

    } else if (key.equals("b") || key.equals("B")) {
      int startingPositionInContentLine = canvasWidth * (y - lineStartY) + x;
      int cursorDestinationIndex = bFindCursorDestinationIndex(startingPositionInContentLine, key, true);

      assignCursorCoordinates(cursorDestinationIndex);

    } else if(key.equals("0")) {
      assignCursorCoordinates(0);
    } else if(key.equals("$")) {
      x = lineEndX;
      y = lineEndY;
      int cursorDestinationIndex = (y - lineStartY) * canvasWidth + x;
      assignCursorCoordinates(cursorDestinationIndex);
      editorModelService.putInterlinearX(1000);
      editorModelService.putInterlinearY(1000);
    } else if(key.equals("^")) {
      int cursorDestination = 0;
      for(int i = 0; i < canvasWidth; i++) {
        if(currentLine.charAt(i) != ' ' && currentLine.charAt(i) != '\t') {
          cursorDestination = i;
          break;
        }
      }
      assignCursorCoordinates(cursorDestination);
    }

}

  public int wFindCursorDestinationIndex(int startingPositionInContentLine, String key, boolean firstIteration) {
    int cursorDestinationIndex = -1;
    String specialCharacters = ".,!?:;\"\'";
    int currentLineNumber = editorModelService.getCurrentLineNumber();
    String currentLine = dataModelService.getContentLines()[currentLineNumber];
    int currentLineLength = currentLine.length();
    char startingChar = currentLine.charAt(startingPositionInContentLine);
    String startingCharAsString = String.valueOf(startingChar);
    boolean startedOnSpecialChar = specialCharacters.contains(startingCharAsString);
    if(startingPositionInContentLine == 0) {
      startingPositionInContentLine++;
    }

    if(!firstIteration) {
      cursorDestinationIndex = 0;
    } else if(currentLineLength > 1) {
      for (int i = startingPositionInContentLine; i <= currentLineLength - 1; i++) {
        char currentChar = currentLine.charAt(i);
        String currentCharAsString = String.valueOf(currentChar);
        char previousChar = currentLine.charAt(i - 1);
        String previousCharAsString = String.valueOf(previousChar);

        boolean currentCharIsSpecialAndShouldBeDestination =
          specialCharacters.contains(currentCharAsString) &&
            !specialCharacters.contains(previousCharAsString) &&
            i != startingPositionInContentLine && key.equals("w");
        boolean currentCharIsRegularNonspaceAndRightAfterSpecial =
          !specialCharacters.contains(currentCharAsString) && key.equals("w") && currentChar != ' ';

        if (currentChar != ' ' && previousChar == ' ' && i != startingPositionInContentLine) {
          cursorDestinationIndex = i;
        } else if (currentCharIsSpecialAndShouldBeDestination) {
          cursorDestinationIndex = i;
        } else if(startedOnSpecialChar) {
          if(currentCharIsRegularNonspaceAndRightAfterSpecial) {
            cursorDestinationIndex = i;
          }
        }
        if (cursorDestinationIndex > -1) {
          break;
        }
      }
      if(cursorDestinationIndex == -1) {
        if(currentLineNumber < dataModelService.getContentLines().length - 1) {
          int nextLineNumber = currentLineNumber + 1;
          editorModelService.putCurrentLineNumber(nextLineNumber);
          String nextLine = dataModelService.getContentLines()[nextLineNumber];
          int nextLineLength = nextLine.length();
          int canvasWidth = editorModelService.getCanvasWidth();
          int numberOfRowsInCurrentLine = currentLineLength / canvasWidth;
          if (nextLineLength % canvasWidth != 0) {
            numberOfRowsInCurrentLine++;
          }
          int lineEndY = lineStartY + numberOfRowsInCurrentLine - 1;
          lineStartY = lineEndY + 1;
          cursorDestinationIndex = wFindCursorDestinationIndex(0, key, false);
        } else {
          cursorDestinationIndex = currentLineLength - 1;
        }
      }
    } else {
      cursorDestinationIndex = 0;
    }
    return cursorDestinationIndex;
  }

  public int bFindCursorDestinationIndex(int startingPositionInContentLine, String key, boolean firstIteration) {
    int cursorDestinationIndex = -1;
    String specialCharacters = ".,!?:;\"\'";
    boolean spaceOrSpecialFound = key.equals("b") && !firstIteration;
    int currentLineNumber = editorModelService.getCurrentLineNumber();
    String currentLine = dataModelService.getContentLines()[currentLineNumber];
    if(currentLine.charAt(startingPositionInContentLine) == ' ') {
      spaceOrSpecialFound = true;
    }

    for (int i = startingPositionInContentLine; i > 0; i--) {
      char currentChar = currentLine.charAt(i);
      String currentCharAsString = String.valueOf(currentChar);
      char previousChar = currentLine.charAt(i - 1);
      String previousCharAsString = String.valueOf(previousChar);

      boolean spaceOrSpecialDestinationTargettedToBeFound =
        previousChar == ' ' && currentChar != ' ' ||
          specialCharacters.contains(currentCharAsString) && !specialCharacters.contains(previousCharAsString);
      boolean atBeginningOfWordAndDestination =
        previousChar == ' ' && currentChar != ' ' && (i != startingPositionInContentLine || !firstIteration);
      boolean previousCharWasSpecialCurrentIsNotAndLowercaseWasPressed =
        specialCharacters.contains(previousCharAsString) &&
          !specialCharacters.contains(currentCharAsString) &&
          key.equals("b") && spaceOrSpecialFound && currentChar != ' ';

      if (spaceOrSpecialDestinationTargettedToBeFound) {
        if (!spaceOrSpecialFound) {
          if (specialCharacters.contains(currentCharAsString) &&
            !specialCharacters.contains(previousCharAsString) &&
            i != startingPositionInContentLine && key.equals("b")) {
            cursorDestinationIndex = i;
          } else if (atBeginningOfWordAndDestination) {
            cursorDestinationIndex = i;
          } else {
            spaceOrSpecialFound = true;
          }
        } else {
          if (!(key.equals("B") && specialCharacters.contains(currentCharAsString))) {
            cursorDestinationIndex = i;
          }
        }
      } else if (previousCharWasSpecialCurrentIsNotAndLowercaseWasPressed) {
        cursorDestinationIndex = i;
      }
      if (cursorDestinationIndex > -1) {
        break;
      }
    }
    if(cursorDestinationIndex == -1) {
      if(currentLineNumber > 0 && !spaceOrSpecialFound && startingPositionInContentLine == 0) {
        int previousLineNumber = currentLineNumber - 1;
        editorModelService.putCurrentLineNumber(previousLineNumber);
        String previousLine = dataModelService.getContentLines()[previousLineNumber];
        int previousLineLength = previousLine.length();
        int canvasWidth = editorModelService.getCanvasWidth();
        int numberOfRowsInPreviousLine = previousLineLength / canvasWidth;
        if (previousLineLength % canvasWidth != 0) {
          numberOfRowsInPreviousLine++;
        }
        lineStartY = lineStartY - numberOfRowsInPreviousLine;
        cursorDestinationIndex = bFindCursorDestinationIndex(previousLineLength - 1, key, false);
      } else {
        cursorDestinationIndex = 0;
      }
    }
    return cursorDestinationIndex;
  }
  public void assignCursorCoordinates(int cursorDestinationIndex) {
    int canvasWidth = editorModelService.getCanvasWidth();
    int interlinearY = cursorDestinationIndex / canvasWidth;
    int x = cursorDestinationIndex % canvasWidth;
    int y = lineStartY + interlinearY;

    editorModelService.putCursorX(x);
    editorModelService.putCursorY(y);
    editorModelService.putInterlinearX(x);
    editorModelService.putInterlinearY(interlinearY);

  }
}
