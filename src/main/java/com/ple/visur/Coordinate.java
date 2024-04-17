package com.ple.visur;

import java.util.Objects;

public class Coordinate {
  int x;
  int y;

  private Coordinate(int x, int y) {
    this.x = x;
    this.y = y;
  }

  public static Coordinate make(int x, int y) {
    return new Coordinate(x, y);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof Coordinate that)) return false;
    return x == that.x && y == that.y;
  }

  @Override
  public int hashCode() {
    return Objects.hash(x, y);
  }
}
