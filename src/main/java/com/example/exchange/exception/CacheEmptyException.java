package com.example.exchange.exception;

public class CacheEmptyException extends RuntimeException {

  private static final String errorMessage = "Exchange rates not available now because cache is empty";

  
  public CacheEmptyException() {
    super(errorMessage);
  }
}
