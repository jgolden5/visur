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
      executeCommandState(keyPressed);
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
        Operator operator = getOperatorFromKeyBuffer();
        determineOperatorAndExecuteCommand(keyPressed, operator);

        matchPossible = currentKeyBuffer.matchPrefix();
      }

      if (!matchPossible) {
        ems.putKeyBuffer(KeysPressed.from(new KeyPressed[]{}));
      }

    }

    bus.send(BusEvent.modelWasChanged.name(), null);

  }

  private void executeCommandState(KeyPressed keyPressed) {
    EditorModelService ems = ServiceHolder.editorModelService;
    int commandCursor = ems.getCommandCursor();
    String commandStateKeyPressed = keyPressed.getKey();
    if(commandStateKeyPressed.equals("Escape")) {
      exitCommandState();
    } else if(commandStateKeyPressed.equals("Enter")) {
      handleCommandStateSentence();
      exitCommandState();
    } else if(commandStateKeyPressed.equals("ArrowLeft") || commandStateKeyPressed.equals("ArrowRight")) {
      switch(commandStateKeyPressed) {
        case "ArrowLeft":
          if(commandCursor > 0) {
            commandCursor--;
            ems.putCommandCursor(commandCursor);
          }
          break;
        case "ArrowRight":
          if(commandCursor < ems.getCommandStateContent().length()) {
            commandCursor++;
            ems.putCommandCursor(commandCursor);
          }
          break;
        default:
          ems.reportError("unexpected error in switch statement for arrow movement in command state");
      }
    } else if(commandStateKeyPressed.equals("Backspace")) {
      String commandStateContent = ems.getCommandStateContent();
      if(commandCursor > 0) {
        String substrBeforeDeletedChar = commandStateContent.substring(0, commandCursor - 1);
        String substrAfterDeletedChar = commandStateContent.substring(commandCursor);
        String newCommandStateContent = substrBeforeDeletedChar + substrAfterDeletedChar;
        ems.putCommandStateContent(newCommandStateContent);
        ems.putCommandCursor(--commandCursor);
      }
    } else if(commandStateKeyPressed.length() == 1) {
      String oldCommandStateContent = ems.getCommandStateContent();
      char charToInsert = commandStateKeyPressed.charAt(0);
      String substrBeforeInsertedChar = oldCommandStateContent.substring(0, commandCursor);
      String substrAfterInsertedChar = oldCommandStateContent.substring(commandCursor);
      String newCommandStateContent = substrBeforeInsertedChar + charToInsert + substrAfterInsertedChar;

      ems.putCommandStateContent(newCommandStateContent);
      ems.putCommandCursor(commandCursor + 1);
    }
  }

  private void handleCommandStateSentence() {
    EditorModelService ems = ServiceHolder.editorModelService;
    String commandStateContent = ems.getCommandStateContent();
    String[] sentence = commandStateContent.split(" ");
    for(String word : sentence) {
      if(word.contains("=")) {
        String[] assignmentArray = word.split("=");
        String varToAssignAsString = assignmentArray[0];
        if(isValidVarToAssign(varToAssignAsString)) {
          EditorModelKey varToAssign = EditorModelKey.valueOf(varToAssignAsString);
          System.out.println("var that should be assigned = " + varToAssign);
        } else {
          ems.reportError("assignment variable in command state is not valid");
        }
      } else {
        if(isValidOperator(word)) {
          Operator wordOperator = Operator.valueOf(word);
          OperatorToService operatorToService = OperatorToService.make();
          OperatorService operatorService = operatorToService.get(wordOperator);
          operatorService.execute(wordOperator);
        } else {
          ems.reportError("operator in command state is not valid");
        }
      }
    }
  }

  private boolean isValidVarToAssign(String varToTest) {
    boolean varIsValid = false;
    for(EditorModelKey v : EditorModelKey.values()) {
      if(v.name().equals(varToTest)) {
        varIsValid = true;
      }
    }
    return varIsValid;
  }

  private boolean isValidOperator(String opToTest) {
    boolean opIsValid = false;
    for(Operator op : Operator.values()) {
      if(op.name().equals(opToTest)) {
        opIsValid = true;
      }
    }
    return opIsValid;
  }

  private void exitCommandState() {
    EditorModelService ems = ServiceHolder.editorModelService;
    ems.putIsInCommandState(false);
    ems.putCommandStateContent("");
    ems.putCommandCursor(0);
    System.out.println("Is out of command state");
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
