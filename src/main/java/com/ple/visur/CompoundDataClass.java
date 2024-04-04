package com.ple.visur;

import java.util.HashMap;
import java.util.Objects;

public class CompoundDataClass implements DataClass {
  HashMap<String, DataClass> subs;
  HashMap<String, Deriver> derivers;
  public static CompoundDataClass make(HashMap<String, DataClass> subs) {
    return new CompoundDataClass(subs);
  }

  public CompoundDataClass(HashMap<String, DataClass> subs) {
    this.subs = subs;
    derivers = new HashMap<>();
  }

  public void putSub(String name, DataClass dc) {
    subs.put(name, dc);
  }

  public DataClass getSub(String name) {
    return subs.get(name);
  }

  public void putDeriver(String toDCName, Deriver deriver) {
    derivers.put(toDCName, deriver);
  }

  public Deriver getDeriver(String name) {
    return derivers.get(name);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof CompoundDataClass that)) return false;
    return Objects.equals(subs, that.subs) && Objects.equals(derivers, that.derivers);
  }

  @Override
  public int hashCode() {
    return Objects.hash(subs, derivers);
  }

}
