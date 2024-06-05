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
    EditorModelCoupler ems = ServiceHolder.editorModelCoupler;
    if(ems.getIsInCommandState()) {
      ServiceHolder.commandStateService.executeCommandState(keyPressed);
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
        if(currentKeyBuffer.contains(KeyPressed.from("Shift"))) {
          currentKeyBuffer.removeFirstElement();
          ems.putKeyBuffer(currentKeyBuffer);
        }
        StringToKeymap keymapMap = ems.getKeymapMap();
        //get key from previously specified keymap
        KeysToVisurCommand keymap = keymapMap.get(ems.getEditorMode());
        //get the command stored in the keymap under keybuffer's key
        VisurCommand currentCommand = keymap.get(currentKeyBuffer);
        CommandExecutionService ces = ServiceHolder.commandExecutionService.make();
        if(currentCommand != null) {
          ces.execute(currentCommand);
        }

        matchPossible = currentKeyBuffer.matchPrefix();
      }

      if (!matchPossible) {
        ems.putKeyBuffer(KeysPressed.from(new KeyPressed[]{}));
      }

    }

    bus.send(BusEvent.modelWasChanged.name(), null);

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
