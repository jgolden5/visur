package com.ple.visur;

import io.vertx.core.shareddata.Shareable;

import java.util.HashMap;
import java.util.Map;

public class KeymapMap implements Shareable {

  final Map<String, Keymap> keymapMap = new HashMap<>();

  public static KeymapMap make() {
    return new KeymapMap();
  }

  public void put(String submode, Keymap keyToOperator) {
    keymapMap.put(submode, keyToOperator);
  }

  public Keymap get(String submode) {
    return keymapMap.get(submode);
  }

}
