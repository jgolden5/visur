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
      int cursorDestinationIndex = 0;
      final String punctuation = ".,!?:\"\'";
      boolean cursorDestinationFoundOnFirstLine = false;
      int currentPositionInContentLine = canvasWidth * (y - lineStartY) + x;
      char startingChar = currentLine.charAt(currentPositionInContentLine);
      String startingCharAsString = String.valueOf(startingChar);
      boolean startingCharIsSpecial = punctuation.contains(startingCharAsString);
      if(currentPositionInContentLine + 1 < currentLineLength) {
        if (startingCharIsSpecial && currentLine.charAt(currentPositionInContentLine + 1) != ' ') {
          cursorDestinationFoundOnFirstLine = true;
          cursorDestinationIndex = currentPositionInContentLine + 1;
        }
      }

      //iterate through current line
      if(!cursorDestinationFoundOnFirstLine) {
        for (int i = currentPositionInContentLine; i < currentLineLength - 1; i++) {
          char currentChar = currentLine.charAt(i);
          char nextChar = currentLine.charAt(i + 1);
          String nextCharAsString = String.valueOf(nextChar);
          boolean nextCharIsSpecial = punctuation.contains(nextCharAsString);
          //determine cursorDestinationIndex
          if (currentChar == ' ' && nextChar != ' ') {
            cursorDestinationIndex = i + 1;
          } else if (nextCharIsSpecial && key.equals("w")) {
            cursorDestinationIndex = i + 1;
          }

          if (cursorDestinationIndex > 0) {
            cursorDestinationFoundOnFirstLine = true;
            break;
          }
        }
      }

      //draw cursor based on cursorDestinationIndex
      boolean notOnLastLine = currentLineNumber + 1 < dataModelService.getContentLines().length;
      if (cursorDestinationFoundOnFirstLine) {
        if (cursorDestinationIndex > 0 && cursorDestinationIndex < currentLineLength) {
          if (cursorDestinationIndex > canvasWidth - 1) {
            y = lineStartY + cursorDestinationIndex / canvasWidth;
            if (y > lineEndY) {
              y = lineEndY;
            }
            x = cursorDestinationIndex - canvasWidth * (y - lineStartY);
          } else {
            x = cursorDestinationIndex;
          }
        }
      } else if (notOnLastLine) {
        x = 0;
        y = lineEndY + 1;
        lineStartY = y;
        editorModelService.putCurrentLineNumber(currentLineNumber + 1);
      } else {
        x = lineEndX;
        y = lineEndY;
      }
      editorModelService.putCursorX(x);
      editorModelService.putCursorY(y);
      editorModelService.putInterlinearX(x);
      editorModelService.putInterlinearY(y - lineStartY);
    } else if (key.equals("b") || key.equals("B")) {
      int startingPositionInContentLine = canvasWidth * (y - lineStartY) + x;
      int cursorDestinationIndex = findCursorDestinationIndex(startingPositionInContentLine, key, true);
      System.out.println("cursor destination index = " + cursorDestinationIndex);

      assignCursorCoordinates(cursorDestinationIndex);
      System.out.println("x = " + editorModelService.getCursorX());
      System.out.println("y = " + editorModelService.getCursorY());
      System.out.println("interlinear x = " + editorModelService.getInterlinearX());
      System.out.println("interlinear y = " + editorModelService.getInterlinearY());

    }
  }

  public int findCursorDestinationIndex(int startingPositionInContentLine, String key, boolean firstIteration) {
    int cursorDestinationIndex = -1;
    String specialCharacters = ".,!?:\"\'";
    boolean spaceOrSpecialFound = key.equals("b") && !firstIteration;
    int currentLineNumber = editorModelService.getCurrentLineNumber();
    String currentLine = dataModelService.getContentLines()[currentLineNumber];

    for (int i = startingPositionInContentLine; i > 0; i--) {
      char currentChar = currentLine.charAt(i);
      String currentCharAsString = String.valueOf(currentChar);
      char previousChar = currentLine.charAt(i - 1);
      String previousCharAsString = String.valueOf(previousChar);
      if (previousChar == ' ' && currentChar != ' ' || specialCharacters.contains(currentCharAsString)) {
        if (!spaceOrSpecialFound) {
          if (specialCharacters.contains(currentCharAsString) && i != startingPositionInContentLine && key.equals("b")) {
            cursorDestinationIndex = i;
          } else if (previousChar == ' ' && currentChar != ' ' && i != startingPositionInContentLine) {
            cursorDestinationIndex = i;
          } else {
            spaceOrSpecialFound = true;
          }
        } else {
          if (!(key.equals("B") && specialCharacters.contains(currentCharAsString))) {
            cursorDestinationIndex = i;
          }
        }
      } else if (specialCharacters.contains(previousCharAsString) && key.equals("b") && spaceOrSpecialFound && currentChar != ' ') {
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
        cursorDestinationIndex = findCursorDestinationIndex(previousLineLength - 1, key, false);
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
