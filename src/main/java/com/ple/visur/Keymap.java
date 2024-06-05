package com.ple.visur;

import io.vertx.core.shareddata.Shareable;

import java.util.HashMap;

public class Keymap implements Shareable {
  final String name;
  private final HashMap<KeyPressed, VisurCommand> keymap = new HashMap<>();
  public KeymapHandler[] handlers = new KeymapHandler[]{};

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
    VisurCommand command = keymap.get(keyPressed);
    for(int i = 0; i < handlers.length; i++) {
      if(command == null) {
        command = handlers[i].toVisurCommand(keyPressed);
      } else {
        break;
      }
    }
    return command;
  }

  public void putHandlers(KeymapHandler[] handlers) {
    this.handlers = handlers;
  }

}
