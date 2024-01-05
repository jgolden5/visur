package com.ple.visur;

public class KeysPressed {
  KeyPressed[] keysPressed;

  public KeysPressed(KeyPressed[] keysPressed) {
    this.keysPressed = keysPressed;
  }

  public static KeysPressed make(KeyPressed[] keysPressed) {
    return new KeysPressed(keysPressed);
  }

  public boolean matchExact(KeyBuffer keyBuffer) {
    return keysPressed.equals(keyBuffer);
  }

  public boolean matchPrefix(KeyPressed[] prefix) {
    return true;
  }

}
