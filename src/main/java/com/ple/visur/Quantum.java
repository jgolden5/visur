package com.ple.visur;

import io.vertx.core.shareddata.Shareable;

import java.util.ArrayList;

public interface Quantum extends Shareable {
  int[] getBoundaries(String editorContent, ArrayList<Integer> newlineIndices, boolean includeTail); //newlineIndices are only needed in CustomQuantums
  int move(String editorContent, ArrayList<Integer> newlineIndices, MovementVector m, int[] bounds);
  String getName();
}
