package com.ple.visur;

public class InsertCharHandler implements KeysToOperatorHandler {

  EditorModelService ems;

  public InsertCharHandler(EditorModelService ems) {
    this.ems = ems;
  }

  public static InsertCharHandler make(EditorModelService ems) {
    return new InsertCharHandler(ems);
  }

  @Override
  public Operator toOperator(KeysPressed keysPressed) {
    KeyPressed[] internalKeysPressed = keysPressed.getKeysPressed();
    boolean keyPressedIsValid = internalKeysPressed.length == 1 && internalKeysPressed[0].getKey().length() == 1;
    if(keyPressedIsValid) {
      return Operator.insertChar;
    } else {
      return null;
    }
  }

}
