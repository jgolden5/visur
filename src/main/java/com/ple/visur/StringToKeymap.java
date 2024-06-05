package com.ple.visur;

import io.vertx.core.shareddata.Shareable;

import java.util.HashMap;
import java.util.Map;

//aka keymapMap

public class StringToKeymap implements Shareable {

  final Map<String, KeysToVisurCommand> keymapMap = new HashMap<>();

  public static StringToKeymap make() {
    return new StringToKeymap();
  }

  public void put(String modeOrSubmodeName, KeysToVisurCommand keyToOperator) {
    keymapMap.put(modeOrSubmodeName, keyToOperator);
  }

  public KeysToVisurCommand get(EditorMode editorMode) {
    return keymapMap.get(editorMode);
  }

}
