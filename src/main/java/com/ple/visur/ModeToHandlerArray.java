package com.ple.visur;
import io.vertx.core.shareddata.Shareable;

import java.util.HashMap;
import java.util.Map;

public class ModeToHandlerArray implements Shareable {

  final Map<EditorMode, KeyToOperatorHandler[]> modeToHandlerArrayMap = new HashMap<>();

  public static ModeToHandlerArray make() {
    return new ModeToHandlerArray();
  }

  public void put(EditorMode editorMode, KeyToOperatorHandler[] keyToOperatorHandlerArray) {
    modeToHandlerArrayMap.put(editorMode, keyToOperatorHandlerArray);
  }

  public KeyToOperatorHandler[] get(EditorMode editorMode) {
    return modeToHandlerArrayMap.get(editorMode);
  }

}
