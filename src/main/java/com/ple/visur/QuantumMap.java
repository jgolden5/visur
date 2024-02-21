package com.ple.visur;

import io.vertx.core.shareddata.Shareable;

import java.util.HashMap;

public class QuantumMap implements Shareable {
  HashMap<String, Quantum> map = new HashMap<>();

  public Quantum get(String qName) {
    return map.get(qName);
  }

  public void put(String qName, Quantum specificQuantumImplementation) {
    map.put(qName, specificQuantumImplementation);
  }

}
