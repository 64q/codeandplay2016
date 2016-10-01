package com.github._64q.codeandplay2016.exception;

public class ReponseInvalideException extends RuntimeException {

  private static final long serialVersionUID = 8377640721493755084L;

  public ReponseInvalideException() {

  }

  public ReponseInvalideException(String message, Throwable cause) {
    super(message, cause);
  }

  public ReponseInvalideException(String message) {
    super(message);
  }

  public ReponseInvalideException(Throwable cause) {
    super(cause);
  }

  
}
