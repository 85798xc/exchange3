package com.example.exchange.exception;

public class CurrencyAlreadyExistException extends RuntimeException {

  private static final String errorMessage =
      "Currency %s already exists";

  public CurrencyAlreadyExistException(String currency) {
    super(errorMessage.formatted(currency));
  }
}
