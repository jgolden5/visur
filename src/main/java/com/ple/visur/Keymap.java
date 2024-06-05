package com.ple.visur;

import io.vertx.core.shareddata.Shareable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

//aka keymap

public class Keymap implements Shareable {
  final String name;
  private final HashMap<KeyPressed, VisurCommand> keymap = new HashMap<>();
  public KeymapHandler[] handlers;

  public Keymap(String name) {
    this.name = name;
  }

  public static Keymap make(String name) {
    return new Keymap(name);
  }


  public void put(KeyPressed keyPressed, VisurCommand visurCommand) {
    keymap.put(keyPressed, visurCommand);
  }

  public VisurCommand get(KeyPressed keyPressed) {
    return keymap.get(keyPressed);
  }

  public void putHandlers(KeymapHandler[] handlers) {
    this.handlers = handlers;
  }

}
