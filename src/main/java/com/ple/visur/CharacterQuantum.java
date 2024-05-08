package com.ple.visur;

import java.util.ArrayList;

public class CharacterQuantum implements Quantum {
  @Override
  public int[] getBoundaries(String editorContent, ArrayList<Integer> newlineIndices, boolean includeTail) {
    int ca = ServiceHolder.editorModelCoupler.getCA();
    return new int[]{ca, ca + 1};
  }

  @Override
  public int move(String editorContent, ArrayList<Integer> newlineIndices, MovementVector mv, int[] bounds) {
    int destination = ServiceHolder.editorModelCoupler.getCA();
    while(mv.dx != 0) {
      if(mv.dx > 0) {
        destination = moveRight(editorContent, newlineIndices);
        mv.dx--;
      } else {
        destination = moveLeft(editorContent, newlineIndices);
        mv.dx++;
      }
    }
    while(mv.dy != 0) {
      if(mv.dy > 0) {
        destination = moveDown(editorContent, newlineIndices);
        mv.dy--;
      } else {
        destination = moveUp(editorContent, newlineIndices);
        mv.dy++;
      }

    }
    return destination;
  }

  private int moveRight(String editorContent, ArrayList<Integer> newlineIndices) {
    int destination = ServiceHolder.editorModelCoupler.getCA();
    if (destination < editorContent.length() - 1) {
      destination++;
    }
    return destination;
  }

  private int moveLeft(String editorContent, ArrayList<Integer> newlineIndices) {
    int destination = ServiceHolder.editorModelCoupler.getCA();
    if (destination > 0) {
      destination--;
    }
    return destination;
  }

  private int moveDown(String editorContent, ArrayList<Integer> newlineIndices) {
    int cx = ServiceHolder.editorModelCoupler.getCX();
    int cy = ServiceHolder.editorModelCoupler.getCY();
    Coordinate destinationAsCoordinate = Coordinate.make(cx, cy);

    return ServiceHolder.editorModelCoupler.getCA();
  }

  private int moveUp(String editorContent, ArrayList<Integer> newlineIndices) {
    int cx = ServiceHolder.editorModelCoupler.getCX();
    int cy = ServiceHolder.editorModelCoupler.getCY();
    Coordinate destinationAsCoordinate = Coordinate.make(cx, cy);

    return ServiceHolder.editorModelCoupler.getCA();
  }

  @Override
  public String getName() {
    return "character";
  }
}
