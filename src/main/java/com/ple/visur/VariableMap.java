package com.ple.visur;

import java.util.HashMap;

public class VariableMap {
  HashMap<String, StringVisurVar> val;

  public VariableMap(HashMap<String, StringVisurVar> map) {
    val = map;
  }

  public static VariableMap make(HashMap<String, StringVisurVar> map) {
    return new VariableMap(map);
  }

  public void put(String key, StringVisurVar var) {
    val.put(key, var);
  }

  public StringVisurVar get(String key) {
    return val.get(key);
  }

}
