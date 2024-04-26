package com.ple.visur;

public class Result<T> {
  T val;
  String error;
  public Result(T val, String error) {
    this.val = val;
    this.error = error;
  }
}
