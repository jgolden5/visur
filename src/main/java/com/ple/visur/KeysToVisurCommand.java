package com.ple.visur;

import io.vertx.core.shareddata.Shareable;

import java.util.HashMap;
import java.util.Map;

public class KeysToVisurCommand implements Shareable {
  private final Map<KeysPressed, VisurCommand> keymap = new HashMap<>();

  public static KeysToVisurCommand make() {
    return new KeysToVisurCommand();
  }


  public void put(KeysPressed keysPressed, VisurCommand visurCommand) {
    keymap.put(keysPressed, visurCommand);
  }

  public VisurCommand get(KeysPressed keysPressed) {
    return keymap.get(keysPressed);
  }

}
