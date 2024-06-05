package com.ple.visur;

public class EditingModeHandler implements KeymapHandler {

  final EditorModelCoupler emc = ServiceHolder.editorModelCoupler;

  public static EditingModeHandler make() {
    return new EditingModeHandler();
  }

  @Override
  public VisurCommand toVisurCommand(KeysPressed keysPressed) {
    final KeymapMap keymapMap = emc.getKeymapMap();
    final String submode = emc.getEditorSubmode();
    final Keymap keymap = keymapMap.get(submode);
    VisurCommand visurCommand = keymap.get(keysPressed);
    return visurCommand;
  }

}
