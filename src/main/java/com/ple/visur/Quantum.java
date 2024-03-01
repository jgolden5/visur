package com.ple.visur;

import io.vertx.core.shareddata.Shareable;

public interface Quantum extends Shareable {
  int[] getBoundaries(String contentLines, int x, int y);
  CursorPosition move(String contentLines, CursorPosition startingPos, MovementVector m, int[] bounds);
}
