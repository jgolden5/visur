package com.ple.visur;

public interface VisurVar {
  String getString();
  int getInt();
  boolean getBoolean();
  void put(String v);
  void put(int v);
  void put(boolean v);
  void put(Object v);
}
