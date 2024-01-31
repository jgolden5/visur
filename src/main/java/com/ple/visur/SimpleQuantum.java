package com.ple.visur;

public class SimpleQuantum implements Quantum {

  String type;

  public SimpleQuantum(String type) {
    this.type = type;
  }

  @Override
  public int[] getBoundaries() {
    int[] bounds = new int[2];
    EditorModelService ems = ServiceHolder.editorModelService;
    int contentX = ems.getGlobalVar("contentX").getInt();
    switch(type) {
      case "character":
        bounds[0] = contentX;
        bounds[1] = contentX + 1;
      default:
        ems.reportError("Quantum not recognized");
    }
    return bounds;
  }

  @Override
  public CursorPosition move(CursorPosition pos, String[] contentLines, MovementVector m) {
    switch(type) {
      
    }
  }
}
