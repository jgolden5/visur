package com.ple.visur;

import io.vertx.core.Future;
import io.vertx.core.json.JsonObject;
import io.vertx.rxjava3.core.eventbus.Message;

import static com.ple.visur.ModelIntKey.*;
import static com.ple.visur.ModelStringKey.*;

public class KeyWasPressedVerticle extends AbstractVisurVerticle {

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
    String currentLine = dataModelService.getContentLines()[editorModelService.getCurrentLineNumber()];
    int currentLineLength = currentLine.length();
    final int canvasWidth = editorModelService.getCanvasWidth();
    int lineEndY = currentLineLength / canvasWidth;
    int lineEndX = currentLineLength % canvasWidth - 1;
    if(lineEndX == -1) {
      lineEndX = canvasWidth - 1; //weird workaround
    }
    System.out.println("line end x = " + lineEndX);
    System.out.println("line end y = " + lineEndY);
    System.out.println("canvas width = " + canvasWidth);
    if(key.equals("h")) {
      System.out.println("x = " + editorModelService.getCursorX());
      if(x > 0) {
        x--;
      }
      editorModelService.putCursorX(x);
      System.out.println("x = " + editorModelService.getCursorX());
    } else if (key.equals("j")) {
      final Integer height = editorModelService.getCanvasHeight();
      System.out.println("height = " + height);
      System.out.println("y = " + y);
      if(y < height - 1) {
        boolean shouldGoDown = !(y + 1 > lineEndY || currentLineLength == canvasWidth * (y + 1));
        boolean shouldAdjustX = shouldGoDown && x > lineEndX && y == lineEndY - 1;
        if(shouldGoDown) {
          y++;
          if(shouldAdjustX) {
            x = lineEndX;
          }
        }
      }
      System.out.println("y = " + y);
      editorModelService.putCursorY(y);
      editorModelService.putCursorX(x);
      System.out.println("y = " + editorModelService.getCursorY());
    } else if (key.equals("k")) {
      if(y > 0) {
        y--;
      }
      editorModelService.putCursorY(y);
    } else if (key.equals("l")) {
      final Integer width = editorModelService.getCanvasWidth();
      System.out.println("width = " + width);
      System.out.println("x = " + x);
      if(x < width - 1) {
        boolean shouldGoRight = !(editorModelService.getCursorX() == lineEndX && editorModelService.getCursorY() >= lineEndY);
        if(shouldGoRight) {
          x++;
        }
      }
      System.out.println("x = " + x);
      editorModelService.putCursorX(x);
      System.out.println("x = " + editorModelService.getCursorX());
    }
    System.out.println(key + " key pressed");
  }

}
