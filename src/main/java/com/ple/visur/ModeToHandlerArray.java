package com.ple.visur;
import io.vertx.core.shareddata.Shareable;

import java.util.HashMap;
import java.util.Map;

public class ModeToHandlerArray implements Shareable {

  final Map<EditorMode, KeysToOperatorHandler[]> modeToHandlerArrayMap = new HashMap<>();

  public static ModeToHandlerArray make() {
    return new ModeToHandlerArray();
  }

  public void put(EditorMode editorMode, KeysToOperatorHandler[] keyToOperatorHandlerArray) {
    modeToHandlerArrayMap.put(editorMode, keyToOperatorHandlerArray);
  }

  public KeysToOperatorHandler[] get(EditorMode editorMode) {
    return modeToHandlerArrayMap.get(editorMode);
  }

}
