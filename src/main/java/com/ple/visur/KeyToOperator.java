package com.ple.visur;

import io.vertx.core.shareddata.Shareable;

import java.util.HashMap;
import java.util.Map;

public class KeyToOperator implements Shareable {
  private final Map<KeyPressed, Operator> keymap = new HashMap<>();

  public static KeyToOperator make() {
    return new KeyToOperator();
  }


  public void put(KeyPressed keyPressed, Operator operator) {
    keymap.put(keyPressed, operator);
  }

  public Operator get(KeyPressed keyPressed) {
    return keymap.get(keyPressed);
  }

}
