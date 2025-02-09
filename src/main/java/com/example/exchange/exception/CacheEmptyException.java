package com.example.exchange.exception;

public class CacheEmptyException extends RuntimeException {

  private static final String errorMessage;

  static {
    errorMessage = "Exchange rates not available now because cache is empty";
  }

  public CacheEmptyException() {
    super(errorMessage);
  }
}
