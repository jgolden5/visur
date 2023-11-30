package com.ple.visur;

import io.vertx.core.shareddata.Shareable;
import io.vertx.rxjava3.core.shareddata.LocalMap;

import java.util.HashMap;
import java.util.Map;

public class ModeToKeymap implements Shareable {

  final Map<EditorMode, KeyToOperator> keymapMap = new HashMap<>();

  public static ModeToKeymap make() {
    return new ModeToKeymap();
  }

  public void put(EditorMode editorMode, KeyToOperator keyToOperator) {
    keymapMap.put(editorMode, keyToOperator);
  }

  public KeyToOperator get(EditorMode editorMode) {
    return keymapMap.get(editorMode);
  }

}
