package com.ple.visur;

import io.vertx.core.shareddata.Shareable;

import java.util.HashMap;

public class VariableMap implements Shareable {
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

  public void remove(String key) {
    val.remove(key);
  }

}
