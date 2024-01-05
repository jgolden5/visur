package com.ple.visur;

import io.vertx.core.shareddata.Shareable;

import java.util.HashMap;
import java.util.Map;

public class ModeToKeymap implements Shareable {

  final Map<EditorMode, KeysToOperator> keymapMap = new HashMap<>();

  public static ModeToKeymap make() {
    return new ModeToKeymap();
  }

  public void put(EditorMode editorMode, KeysToOperator keyToOperator) {
    keymapMap.put(editorMode, keyToOperator);
  }

  public KeysToOperator get(EditorMode editorMode) {
    return keymapMap.get(editorMode);
  }

}
