package com.ple.visur;

public class InsertCharHandler implements KeyToOperatorHandler {

  EditorModelService ems;

  public InsertCharHandler(EditorModelService ems) {
    this.ems = ems;
  }

  public static InsertCharHandler make(EditorModelService ems) {
    return new InsertCharHandler(ems);
  }

  @Override
  public Operator toOperator(KeysPressed keysPressed) {
    boolean keyPressedIsValid = keysPressed.keysPressed.length == 1;
    if(keyPressedIsValid) {
      return Operator.insertChar;
    } else {
      return null;
    }
  }

}
