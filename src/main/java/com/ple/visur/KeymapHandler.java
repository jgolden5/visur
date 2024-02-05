package com.ple.visur;

public class KeymapHandler implements KeysToOperatorHandler {

  EditorModelService ems;

  public KeymapHandler(EditorModelService ems) {
    this.ems = ems;
  }

  public static KeymapHandler make(EditorModelService ems) {
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
