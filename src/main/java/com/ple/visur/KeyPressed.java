package com.ple.visur;

public class KeyPressed {
  private final String key;

  public KeyPressed(String key) {
    this.key = key;
  }

  public static KeyPressed from(String key) {
    return new KeyPressed(key);
  }

}
