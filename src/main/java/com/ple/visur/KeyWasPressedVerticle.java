package com.ple.visur;

import io.vertx.core.Future;
import io.vertx.core.json.JsonObject;
import io.vertx.rxjava3.core.eventbus.Message;

public class KeyWasPressedVerticle extends AbstractVisurVerticle {
  int lineStartY = 0;
  int lineStartX = 0;

  @Override
  public void start() {
    vertx.eventBus().consumer(BusEvent.keyWasPressed.name(), this::handle);
  }

  public void handle(Message event) {
    JsonObject keyJson = new JsonObject((String)event.body());
    final String key = keyJson.getString("key");
    mapKeys(key);
    boolean modelChanged = true;
    if(modelChanged) {
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
    int lineEndY = lineStartY + currentLineLength / canvasWidth;
    if(currentLineLength % canvasWidth == 0) {
      lineEndY--;
    }
    int lineEndX = lineStartX + currentLineLength % canvasWidth - 1;
    if(lineEndX == -1) {
      lineEndX = canvasWidth - 1; //weird workaround
    }
    int interlinearX = editorModelService.getInterlinearX();

    if(key.equals("h")) {
      boolean shouldGoUp = y > lineStartY && x == 0;
      boolean shouldGoLeft = !shouldGoUp && x > 0;
      if(shouldGoLeft) {
        x--;
      } else if(shouldGoUp) {
        y--;
        x = canvasWidth - 1;
        editorModelService.putCursorY(y);
      }
      editorModelService.putCursorX(x);
      editorModelService.putInterlinearX(x);
    } else if (key.equals("j")) {
      String nextLine = currentLineNumber + 1 == dataModelService.getContentLines().length ?
        "" : dataModelService.getContentLines()[currentLineNumber + 1];
      int nextLineLength = nextLine.length();

      if(y < canvasHeight - 1) {
        boolean endOfCurrentLine = y == lineEndY && currentLineNumber + 1 < dataModelService.getContentLines().length;
        boolean shouldGoDown;
        int nextMaxX = 0;
        if(endOfCurrentLine) {
          if(nextLineLength <= canvasWidth) {
            nextMaxX = nextLineLength - 1;
          } else {
            nextMaxX = canvasWidth - 1;
          }
        } else {
          if(y == lineEndY) {
            nextMaxX = lineEndX;
          } else if(y + 1 == lineEndY) {
            if(currentLineLength % canvasWidth == 0) {
              nextMaxX = canvasWidth - 1;
            } else {
              nextMaxX = currentLineLength % canvasWidth - 1;
            }
          } else {
            nextMaxX = canvasWidth - 1;
          }
        }
        shouldGoDown = currentLineNumber < dataModelService.getContentLines().length - 1;
        if(shouldGoDown) {
          y = lineEndY + 1;
          editorModelService.putCurrentLineNumber(currentLineNumber + 1);
          lineStartY = y;
          if(nextMaxX >= interlinearX) {
            x = interlinearX;
          } else {
            x = nextMaxX;
          }
        }
      }
      editorModelService.putCursorY(y);
      editorModelService.putCursorX(x);
    } else if (key.equals("k")) {
      String previousLine = currentLineNumber - 1 < 0 ?
        "" : dataModelService.getContentLines()[currentLineNumber - 1];
      int previousLineLength = previousLine.length();

      if(y > 0) {
        boolean beginningOfCurrentLine = y == lineStartY;
        boolean shouldGoUp;
        int lastMaxX = 0;
        //
        if(beginningOfCurrentLine && previousLineLength % canvasWidth != 0) {
          lastMaxX = previousLineLength % canvasWidth - 1;
        } else {
          lastMaxX = canvasWidth - 1;
        }
        //
        shouldGoUp = lineStartY > 0;
        if(shouldGoUp) {
          lineStartY = lineStartY - 1 - previousLineLength / canvasWidth;
          y = lineStartY;
          editorModelService.putCurrentLineNumber(currentLineNumber - 1);
          if(lastMaxX >= interlinearX) {
            x = interlinearX;
          } else {
            x = lastMaxX;
          }
        }
      }
      editorModelService.putCursorY(y);
      editorModelService.putCursorX(x);
    } else if (key.equals("l")) {
      final Integer width = editorModelService.getCanvasWidth();
      boolean shouldGoRight;
      boolean shouldGoDown;
      if(y < lineEndY) {
        if(x < canvasWidth - 1) {
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

      if(shouldGoRight) {
        x++;
      } else if(shouldGoDown) {
        y++;
        x = 0;
        editorModelService.putCursorY(y);
      }
      editorModelService.putCursorX(x);
      editorModelService.putInterlinearX(x);
    }
  }

  private void updateLineStartY(int lineEndY) {
    String currentContentLine = dataModelService.getContentLines()[editorModelService.getCurrentLineNumber()];
    int currentContentLineLength = currentContentLine.length();
    int canvasWidth = editorModelService.getCanvasWidth();
    lineStartY = lineEndY - currentContentLineLength / canvasWidth;
    if(currentContentLineLength % canvasWidth == 0) {
      lineStartY++;
    }
  }

}
