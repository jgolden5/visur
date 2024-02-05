package com.ple.visur;

import java.util.ArrayList;

public class VisurCommand {
  String sentenceAsString;
  ArrayList<Operator> ops = new ArrayList<>();
  ArrayList<Object> opInfo = new ArrayList<>();

  public VisurCommand(String s) {
    sentenceAsString = s;
  }

  public void addOperatorWithInfo(Object[] operatorWithInfo) {
    if(operatorWithInfo.length != 2) {
      System.out.println("Error, incorrect size for operator info!");
    } else {
      ops.add((Operator) operatorWithInfo[0]);
      opInfo.add(operatorWithInfo[1]);
    }
  }
}
