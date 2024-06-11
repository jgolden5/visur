package com.ple.visur;

public enum EditorSubmode {
  editing,
  insert,
  quantumStart,
  quantumEnd,
  span,
  ;

  public static EditorSubmode getSubmodeByString(String str) {
    for(EditorSubmode submode : EditorSubmode.values()) {
      if(submode.name().equals(str)) {
        return submode;
      }
    }
    return null;
  }
}
