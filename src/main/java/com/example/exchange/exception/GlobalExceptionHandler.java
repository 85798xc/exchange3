package com.example.exchange.exception;


import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolationException;
import java.time.Instant;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(ConstraintViolationException.class)
  public ResponseEntity<ErrorResponse> handleConstraintViolationException(
      ConstraintViolationException e, HttpServletRequest request) {
    ErrorResponse errorResponse = new ErrorResponse(
        request.getRequestURI(),
        e.getMessage(),
        Instant.now(),
        HttpStatus.BAD_REQUEST.value());

    return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(CurrencyAlreadyExistException.class)
  public ResponseEntity<ErrorResponse> handleCurrencyAlreadyExistsException(
      CurrencyAlreadyExistException e, HttpServletRequest request) {

    ErrorResponse errorResponse = new ErrorResponse(
        request.getRequestURI(),
        e.getMessage(),
        Instant.now(),
        HttpStatus.CONFLICT.value()

    );

    return new ResponseEntity<>(errorResponse, HttpStatus.CONFLICT);
  }

  @ExceptionHandler(CacheEmptyException.class)
  public ResponseEntity<ErrorResponse> handleCacheEmptyException(CacheEmptyException e,
      HttpServletRequest request) {

    ErrorResponse errorResponse = new ErrorResponse(
        request.getRequestURI(),
        e.getMessage(),
        Instant.now(),
        HttpStatus.SERVICE_UNAVAILABLE.value());
    return new ResponseEntity<>(errorResponse, HttpStatus.SERVICE_UNAVAILABLE);
  }

  @ExceptionHandler(NoSuchCurrencyExistsException.class)
  public ResponseEntity<ErrorResponse> handleNoSuchCurrencyExistsException(
      NoSuchCurrencyExistsException e, HttpServletRequest request) {

    ErrorResponse errorResponse = new ErrorResponse(
        request.getRequestURI(),
        e.getMessage(),
        Instant.now(),
        HttpStatus.NO_CONTENT.value());

    return new ResponseEntity<>(errorResponse, HttpStatus.NO_CONTENT);
  }

}
