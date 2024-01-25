package com.ple.visur;

import java.util.HashMap;
import java.util.Map;

public class VariableMap {
  Map<String, VisurVar> val = new HashMap<String, VisurVar>();

  public VariableMap(Map<String, VisurVar> map) {
    val = map;
  }

  public static VariableMap make(Map<String, VisurVar> map) {
    return new VariableMap(map);
  }

}
