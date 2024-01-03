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

    //go from keyPressed to handlers to operator
    if(keyPressed.getKey().equals(";")) {
      ems.putIsInCommandState(true);
      System.out.println("Is in command state");
    } else {
      ModeToHandlerArray modeToHandlerArrayMap = ems.getModeToHandlerArray();
      KeyToOperatorHandler[] handlerArray = modeToHandlerArrayMap.get(mode);
      int i = 0;
      Operator operator = null;
      boolean shouldExit = false;
      while (operator == null && !shouldExit) {
        if (i < handlerArray.length) {
          operator = handlerArray[i].toOperator(keyPressed);
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
