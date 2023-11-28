package com.ple.visur;

import io.vertx.core.shareddata.Shareable;

public class KeymapMap implements Shareable {

  EditorMode editorMode;

  Keymap keymap;

  public Keymap get(EditorMode mode) {
    switch(mode) {
      case normal:
        keymap = keymap.get(normalKeymap);
        return normalKeymap;
        break;
      case insert:
        return insertKeymap;
        break;
      default:
        System.out.println("editor mode not recognized");
    }
  }

  public void put() {

  }

}
