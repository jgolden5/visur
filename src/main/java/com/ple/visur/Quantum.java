package com.ple.visur;

import io.vertx.core.shareddata.Shareable;

import java.util.ArrayList;

public interface Quantum extends Shareable {
  int[] getBoundaries(String editorContent, ArrayList<Integer> newlineIndices, int x, int y, boolean includeTail); //newlineIndices are only needed in CustomQuantums
  CursorPosition move(String editorContent, ArrayList<Integer> newlineIndices, CursorPosition startingPos, MovementVector m, int[] bounds);
  String getName();
}
