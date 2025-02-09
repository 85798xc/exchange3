package com.example.exchange.exception;

public class NoSuchCurrencyExistsException extends RuntimeException {

  private static final String errorMessage;

  static {
    errorMessage = "Currency %s is not supported yet";
  }

  public NoSuchCurrencyExistsException(String currency) {
    super(errorMessage.formatted(currency));
  }

}
