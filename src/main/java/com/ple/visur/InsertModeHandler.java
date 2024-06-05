package com.ple.visur;

public class InsertModeHandler implements KeymapHandler {

  EditorModelCoupler ems;

  public static InsertModeHandler make() {
    return new InsertModeHandler();
  }

  @Override
  public VisurCommand toVisurCommand(KeyPressed keyPressed) {
//    if(keyPressedIsValid) {
//      return Operator.insertChar;
//    } else {
      return null;
//    }
  }

}
