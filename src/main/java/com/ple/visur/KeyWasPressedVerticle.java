package com.ple.visur;

import io.vertx.core.Future;
import io.vertx.core.json.JsonObject;
import io.vertx.rxjava3.core.eventbus.Message;

public class KeyWasPressedVerticle extends AbstractVisurVerticle {

  @Override
  public void start() {
    vertx.eventBus().consumer(BusEvent.keyWasPressed.name(), this::handle);
  }

  public void handle(Message event) {
    EditorModelService ems = ServiceHolder.editorModelService;
    JsonObject keyJson = new JsonObject((String) event.body());
    final String key = keyJson.getString("key");
    KeyPressed keyPressed = KeyPressed.from(key);
    EditorMode mode = ems.getEditorMode();
    boolean matchPossible; //if false, buffer gets erased and replaced, else buffer gets saved
    KeysPressed previousKeyBuffer = ems.getKeyBuffer();
    KeysPressed currentKeyBuffer;
    if(previousKeyBuffer.getKeysPressed().length == 0) {
      currentKeyBuffer = KeysPressed.from(new KeyPressed[]{keyPressed});
    } else {
      KeyPressed[] previousKeyBufferKeys = previousKeyBuffer.getKeysPressed();
      KeyPressed[] keysToAddToCurrentKeyBuffer = new KeyPressed[previousKeyBufferKeys.length + 1];
      for(int i = 0; i < previousKeyBufferKeys.length; i++) {
        keysToAddToCurrentKeyBuffer[i] = previousKeyBufferKeys[i];
      }
      keysToAddToCurrentKeyBuffer[previousKeyBufferKeys.length] = keyPressed;
      currentKeyBuffer = KeysPressed.from(keysToAddToCurrentKeyBuffer);
    }
    ems.putKeyBuffer(currentKeyBuffer);
    KeysPressed keysRequiredToEnterCommandState = new KeysPressed(
      new KeyPressed[]{KeyPressed.from("Shift"), KeyPressed.from("Escape")}
    );
    KeysPressed onlyShiftKeyPressed = KeysPressed.from(new KeyPressed[]{KeyPressed.from("Shift")});

    if(currentKeyBuffer.matchExact(keysRequiredToEnterCommandState)) {
      ems.putIsInCommandState(true);
      System.out.println("Is in command state");
    } else if(currentKeyBuffer.matchExact(onlyShiftKeyPressed)) {
      matchPossible = true;
    } else {
      ModeToHandlerArray modeToHandlerArrayMap = ems.getModeToHandlerArray();
      KeysToOperatorHandler[] handlerArray = modeToHandlerArrayMap.get(mode);
      int i = 0;
      Operator operator = null;
      boolean shouldExit = false;
      while (operator == null && !shouldExit) {
        if (i < handlerArray.length) {
          operator = handlerArray[i].toOperator(currentKeyBuffer);
        } else {
//        ems.reportError("Invalid key"); //make an error message line that displays onscreen eventually
          shouldExit = true;
        }
        i++;
      }

      if (operator != null) {
        OperatorToService operatorToService = OperatorToService.make();
        OperatorService operatorService = operatorToService.get(operator);
        boolean operatorServiceIsInsertCharService = operatorService.getClass().getSimpleName().equals("InsertCharService");
        if (operatorServiceIsInsertCharService) {
          operatorService.execute(operator, keyPressed);
        } else {
          operatorService.execute(operator);
        }
        bus.send(BusEvent.modelWasChanged.name(), null);
      }

    }

    matchPossible = currentKeyBuffer.matchPrefix();
    if(!matchPossible) {
      ems.putKeyBuffer(KeysPressed.from(new KeyPressed[]{}));
    }

  }

  public Future<Void> keyPress(String key) {
    return Future.succeededFuture();
  }

}
