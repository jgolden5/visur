package com.ple.visur;

public class KeymapHandler implements KeyToOperatorHandler {

  EditorModelService ems;

  public KeymapHandler(EditorModelService ems) {
    this.ems = ems;
  }

  public static KeymapHandler make(EditorModelService ems) {
    return new KeymapHandler(ems);
  }

  @Override
  public Operator toOperator(KeyPressed keyPressed) {
    final ModeToKeymap keymapMap = ems.getKeymapMap();
    final EditorMode mode = ems.getEditorMode();
    final KeyToOperator keymap = keymapMap.get(mode);
    Operator operator = keymap.get(keyPressed);
    return operator;
  }

}
