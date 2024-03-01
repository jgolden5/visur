package com.ple.visur;

import io.vertx.core.shareddata.Shareable;

public interface Quantum extends Shareable {
  int[] getBoundaries(String editorContent, int[] newlineIndices, int x, int y); //newlineIndices are only needed in CustomQuantums
  CursorPosition move(String editorContent, int[] newlineIndices, CursorPosition startingPos, MovementVector m, int[] bounds);
}
