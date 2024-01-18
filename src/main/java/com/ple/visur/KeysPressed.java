package com.ple.visur;

import io.vertx.core.shareddata.Shareable;

import java.util.Arrays;
import java.util.Objects;

public class KeysPressed implements Shareable {
  private KeyPressed[] keysPressed;

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    KeysPressed that = (KeysPressed) o;
    return Arrays.equals(keysPressed, that.keysPressed);
  }

  @Override
  public int hashCode() {
    return Objects.hash(keysPressed);
  }

  public KeysPressed(KeyPressed[] keysPressed) {
    this.keysPressed = keysPressed;
  }

  public static KeysPressed from(KeyPressed[] keysPressed) {
    return new KeysPressed(keysPressed);
  }

  public KeyPressed[] getKeysPressed() {
    return keysPressed;
  }

  public boolean matchExact(KeysPressed keysPressed) {
    return this.equals(keysPressed);
  }

  public boolean matchPrefix() {
    return false;
  }

  public boolean contains(KeyPressed key) {
    boolean keysPressedContainsKey = false;
    for(int i = 0; i < keysPressed.length; i++) {
      if(keysPressed[i].equals(key)) {
        keysPressedContainsKey = true;
        break;
      }
    }
    return keysPressedContainsKey;
  }

  public void putKeysPressed(KeyPressed[] keysPressed) {
    this.keysPressed = keysPressed;
  }

  public void removeFirstElement() {
    KeyPressed[] newKeysPressed = new KeyPressed[keysPressed.length - 1];
    for(int i = 0; i < keysPressed.length - 1; i++) {
      newKeysPressed[i] = keysPressed[i + 1];
    }
    keysPressed = newKeysPressed;
  }

}
