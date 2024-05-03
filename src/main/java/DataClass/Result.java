package DataClass;

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

  public void putVal(T val) {
    this.val = val;
  }

  public void putError(String error) {
    this.error = error;
  }

  public T getVal() {
    return val;
  }

  public String getError() {
    return error;
  }

}
