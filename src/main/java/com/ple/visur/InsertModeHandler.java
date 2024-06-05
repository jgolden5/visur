package com.ple.visur;

public class InsertModeHandler implements KeymapHandler {

  EditorModelCoupler ems;

  public static InsertModeHandler make() {
    return new InsertModeHandler();
  }

  @Override
  public VisurCommand toVisurCommand(KeysPressed keysPressed) {
    KeyPressed[] internalKeysPressed = keysPressed.getKeysPressed();
    boolean keyPressedIsValid = internalKeysPressed.length == 1 && internalKeysPressed[0].getKey().length() == 1;
//    if(keyPressedIsValid) {
//      return Operator.insertChar;
//    } else {
      return null;
//    }
  }

}
