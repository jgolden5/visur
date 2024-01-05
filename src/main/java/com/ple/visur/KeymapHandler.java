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
  public Operator toOperator(KeysPressed keysPressed) {
    final ModeToKeymap keymapMap = ems.getKeymapMap();
    final EditorMode mode = ems.getEditorMode();
    final KeysToOperator keymap = keymapMap.get(mode);
    Operator operator = keymap.get(keysPressed);
    return operator;
  }

}
