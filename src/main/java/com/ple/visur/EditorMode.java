package com.ple.visur;

public enum EditorMode {
  navigate,
  insert,
  reading;
  public static EditorMode getModeByString(String str) {
    for(EditorMode mode : EditorMode.values()) {
      if(mode.name().equals(str)) {
        return mode;
      }
    }
    return null;
  }
}
