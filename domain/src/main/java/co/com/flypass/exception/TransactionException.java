package co.com.flypass.exception;

public class TransactionException extends ErrorException {

  public TransactionException(String message, Throwable cause) {
    super(message, cause);
  }
}
