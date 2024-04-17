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
    final ModeToKeymap keymapMap = ems.getKeymapMap();
    final EditorMode mode = ems.getEditorMode();
    final KeysToVisurCommand keymap = keymapMap.get(mode);
    VisurCommand visurCommand = keymap.get(keysPressed);
    return visurCommand;
  }

}
