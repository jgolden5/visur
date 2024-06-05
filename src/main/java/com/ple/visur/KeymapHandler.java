package com.ple.visur;

public class KeymapHandler implements KeysToOperatorHandler {

  EditorModelCoupler ems;

  public KeymapHandler(EditorModelCoupler ems) {
    this.ems = ems;
  }

  public static KeymapHandler make(EditorModelCoupler ems) {
    return new KeymapHandler(ems);
  }

  @Override
  public VisurCommand toVisurCommand(KeysPressed keysPressed) {
    final StringToKeymap keymapMap = ems.getKeymapMap();
    final String submode = ems.getEditorSubmode();
    final KeysToVisurCommand keymap = keymapMap.get(submode);
    VisurCommand visurCommand = keymap.get(keysPressed);
    return visurCommand;
  }

}
