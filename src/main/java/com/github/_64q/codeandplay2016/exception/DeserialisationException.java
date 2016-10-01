package com.github._64q.codeandplay2016.exception;

public class DeserialisationException extends RuntimeException {

  private static final long serialVersionUID = 1195121367590902036L;

  public DeserialisationException() {
  }

  public DeserialisationException(String message, Throwable cause) {
    super(message, cause);
  }

  public DeserialisationException(String message) {
    super(message);
  }

  public DeserialisationException(Throwable cause) {
    super(cause);
  }
  
}
