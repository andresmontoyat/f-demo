package co.com.flypass.exception;

import lombok.Getter;

@Getter
public class ErrorException extends RuntimeException {

  private final Object[] messageArgs;

  public ErrorException(String message, Object... messagesArgs) {
    super(message);
    this.messageArgs = messagesArgs;
  }

  public ErrorException(String message, String messageKey, Throwable cause,
      Object... messagesArgs) {
    super(message, cause);
    this.messageArgs = messagesArgs;
  }
}
