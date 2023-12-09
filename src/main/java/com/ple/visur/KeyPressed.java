package com.ple.visur;

import java.util.Objects;

public class KeyPressed {
  private final String key;

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    KeyPressed that = (KeyPressed) o;
    return Objects.equals(key, that.key);
  }

  @Override
  public int hashCode() {
    return Objects.hash(key);
  }

  public KeyPressed(String key) {
    this.key = key;
  }

  public String getKey() {
    return key;
  }

  public static KeyPressed from(String key) {
    return new KeyPressed(key);
  }

}
