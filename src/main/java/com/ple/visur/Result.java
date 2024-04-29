package com.ple.visur;

public class Result<T> {
  T val;
  String error;
  public Result(T val, String error) {
    this.val = val;
    this.error = error;
  }
  public static <T> Result make(T val, String error) {
    return new Result(val, error);
  }

  public T getVal() {
    return val;
  }

  public String getError() {
    return error;
  }

}
