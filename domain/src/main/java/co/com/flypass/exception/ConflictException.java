package co.com.flypass.exception;

public class ConflictException extends ErrorException {

  public ConflictException(String message, Object... messageArgs) {
    super(message, messageArgs);
  }
}
