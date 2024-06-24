package co.com.flypass.exception;

public class ResourceNotFoundException extends ErrorException {

  public ResourceNotFoundException(String message, Object... messageArgs) {
    super(message, messageArgs);
  }
}
