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
    JsonObject keyJson = new JsonObject((String) event.body());
    final String key = keyJson.getString("key");
    KeyPressed keyPressed = KeyPressed.from(key);
    EditorModelService ems = ServiceHolder.editorModelService;
    if(ems.getIsInCommandState()) {
      if(keyPressed.getKey().equals("Enter") || keyPressed.getKey().equals("Escape")) {
        ems.putIsInCommandState(false);
        System.out.println("Is out of command state");
      }
    } else {
      boolean matchPossible; //if false, buffer gets erased and replaced, else buffer gets saved
      KeysPressed previousKeyBuffer = ems.getKeyBuffer();
      KeysPressed currentKeyBuffer = determineCurrentKeyBuffer(previousKeyBuffer, keyPressed);
      ems.putKeyBuffer(currentKeyBuffer);
      final KeysPressed keysRequiredToEnterCommandState = new KeysPressed(
        new KeyPressed[]{KeyPressed.from("Shift"), KeyPressed.from("Escape")}
      );
      final KeysPressed onlyShiftKeyPressed = KeysPressed.from(new KeyPressed[]{KeyPressed.from("Shift")});

      if (currentKeyBuffer.matchExact(keysRequiredToEnterCommandState)) {
        ems.putIsInCommandState(true);
        matchPossible = false;
        System.out.println("Is in command state");
      } else if (currentKeyBuffer.matchExact(onlyShiftKeyPressed)) {
        matchPossible = true;
      } else {
        Operator operator = getOperatorFromKeyBuffer();
        determineOperatorAndExecuteCommand(keyPressed, operator);

        matchPossible = currentKeyBuffer.matchPrefix();
      }

      if (!matchPossible) {
        ems.putKeyBuffer(KeysPressed.from(new KeyPressed[]{}));
      }

    }

  }

  private void determineOperatorAndExecuteCommand(KeyPressed keyPressed, Operator operator) {
    if(operator != null) {
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

  private Operator getOperatorFromKeyBuffer() {
    EditorModelService ems = ServiceHolder.editorModelService;
    KeysPressed currentKeyBuffer = ems.getKeyBuffer();
    EditorMode mode = ems.getEditorMode();
    ModeToHandlerArray modeToHandlerArrayMap = ems.getModeToHandlerArray();
    KeysToOperatorHandler[] handlerArray = modeToHandlerArrayMap.get(mode);
    int i = 0;
    Operator operator = null;
    boolean shouldExit = false;
    while (operator == null && !shouldExit) {
      if (i < handlerArray.length) {
        operator = handlerArray[i].toOperator(currentKeyBuffer);
      } else {
        ems.reportError("Invalid key");
        shouldExit = true;
      }
      i++;
    }
    return operator;
  }

  private KeysPressed determineCurrentKeyBuffer(KeysPressed previousKeyBuffer, KeyPressed keyPressed) {
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
    return currentKeyBuffer;
  }


  public Future<Void> keyPress(String key) {
    return Future.succeededFuture();
  }

}
