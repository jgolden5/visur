package com.ple.visur;

public class KeyBuffer {
  private KeysPressed keysPressed;

  public KeyBuffer(KeysPressed keysPressed) {
    this.keysPressed = keysPressed;
  }

  public static KeyBuffer make(KeysPressed keysPressed) {
    return new KeyBuffer(keysPressed);
  }

  public KeysPressed getKeys() {
    return keysPressed;
  }

}
