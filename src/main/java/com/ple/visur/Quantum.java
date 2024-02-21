package com.ple.visur;

import io.vertx.core.shareddata.Shareable;

public interface Quantum extends Shareable {
  int[] getBoundaries();
  CursorPosition move(String[] contentLines, CursorPosition pos, MovementVector m);
}
