package com.ple.visur;

import java.util.HashMap;

public class VariableMap {
  HashMap<String, VisurVar> val;

  public VariableMap(HashMap<String, VisurVar> map) {
    val = map;
  }

  public static VariableMap make(HashMap<String, VisurVar> map) {
    return new VariableMap(map);
  }

  public void put(String key, VisurVar var) {
    val.put(key, var);
  }

  public VisurVar get(String key) {
    return val.get(key);
  }

}
