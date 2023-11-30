package com.ple.visur;

import io.vertx.core.shareddata.Shareable;

import java.util.HashMap;
import java.util.Map;

public class KeyToOperator implements Shareable {
  final Map<KeyPressed, Operator> keyMap = new HashMap<>();

  public static KeyToOperator make(EditorMode mode) {
    return new KeyToOperator();
  }


  public void put(KeyPressed keyPressed, Operator operator) {
    keyMap.put(keyPressed, operator);
  }

  public Operator get(KeyPressed keyPressed) {
    return keyMap.get(keyPressed);
  }
}
