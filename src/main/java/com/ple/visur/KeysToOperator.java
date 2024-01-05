package com.ple.visur;

import io.vertx.core.shareddata.Shareable;

import java.util.HashMap;
import java.util.Map;

public class KeysToOperator implements Shareable {
  private final Map<KeysPressed, Operator> keymap = new HashMap<>();

  public static KeysToOperator make() {
    return new KeysToOperator();
  }


  public void put(KeysPressed keysPressed, Operator operator) {
    keymap.put(keysPressed, operator);
  }

  public Operator get(KeysPressed keysPressed) {
    return keymap.get(keysPressed);
  }

}
