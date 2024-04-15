package com.ple.visur;

import java.util.Objects;

public class CursorPosition {
  int x;
  int y;

  private CursorPosition(int x, int y) {
    this.x = x;
    this.y = y;
  }

  public static CursorPosition make(int x, int y) {
    return new CursorPosition(x, y);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof CursorPosition that)) return false;
    return x == that.x && y == that.y;
  }

  @Override
  public int hashCode() {
    return Objects.hash(x, y);
  }
}
