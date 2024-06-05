package com.ple.visur;

import io.vertx.core.shareddata.Shareable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

//aka keymap

public class Keymap implements Shareable {
  final String name;
  private final HashMap<KeysPressed, VisurCommand> keymap = new HashMap<>();
  public ArrayList<KeymapHandler> handlers = new ArrayList<>();

  public Keymap(String name) {
    this.name = name;
  }

  public static Keymap make(String name) {
    return new Keymap(name);
  }


  public void put(KeysPressed keysPressed, VisurCommand visurCommand) {
    keymap.put(keysPressed, visurCommand);
  }

  public VisurCommand get(KeysPressed keysPressed) {
    return keymap.get(keysPressed);
  }

}
