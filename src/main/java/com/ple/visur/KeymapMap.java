package com.ple.visur;

import io.vertx.core.shareddata.Shareable;

import java.util.HashMap;
import java.util.Map;

public class KeymapMap implements Shareable {

  final Map<EditorSubmode, Keymap> keymapMap = new HashMap<>();

  public static KeymapMap make() {
    return new KeymapMap();
  }

  public void put(EditorSubmode submode, Keymap keyToOperator) {
    keymapMap.put(submode, keyToOperator);
  }

  public Keymap get(EditorSubmode submode) {
    return keymapMap.get(submode);
  }

}
