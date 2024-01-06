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
    ems.putKeyPressed(keyPressed);
    boolean matchPossible; //if false, buffer gets erased and replaced, else buffer gets saved
    KeysPressed keyBuffer;
    KeyPressed[] keyPressedArray = new KeyPressed[]{keyPressed};
    keyBuffer = KeysPressed.from(keyPressedArray);
    ems.putKeyBuffer(keyBuffer);
    KeysPressed keysRequiredToEnterCommandState = new KeysPressed(
      new KeyPressed[]{KeyPressed.from("Shift"), KeyPressed.from("Escape")}
    );

    //go from keyPressed to handlers to operator
    if(keyBuffer.matchExact(keysRequiredToEnterCommandState)) {
      ems.putIsInCommandState(true);
      System.out.println("Is in command state");
    } else if(keyBuffer.matchExact(KeysPressed.from(new KeyPressed[]{KeyPressed.from("Shift")}))) {
      matchPossible = true;
    } else {
      ModeToHandlerArray modeToHandlerArrayMap = ems.getModeToHandlerArray();
      KeysToOperatorHandler[] handlerArray = modeToHandlerArrayMap.get(mode);
      int i = 0;
      Operator operator = null;
      boolean shouldExit = false;
      while (operator == null && !shouldExit) {
        if (i < handlerArray.length) {
          operator = handlerArray[i].toOperator(keyBuffer);
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

  }

  public Future<Void> keyPress(String key) {
    return Future.succeededFuture();
  }

}
