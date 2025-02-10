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

  private static ErrorResponse makeErrorResponse(HttpServletRequest request, Exception e) {
    return new ErrorResponse(request.getRequestURI(), e.getMessage(), Instant.now());
  }

  @ExceptionHandler(ConstraintViolationException.class)
  public ResponseEntity<ErrorResponse> handleConstraintViolationException(
      ConstraintViolationException e, HttpServletRequest request) {

    ErrorResponse errorResponse = makeErrorResponse(request, e);

    return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(CurrencyAlreadyExistException.class)
  public ResponseEntity<ErrorResponse> handleCurrencyAlreadyExistsException(
      CurrencyAlreadyExistException e, HttpServletRequest request) {

    ErrorResponse errorResponse = makeErrorResponse(request, e);

    return new ResponseEntity<>(errorResponse, HttpStatus.CONFLICT);
  }

  @ExceptionHandler(CacheEmptyException.class)
  public ResponseEntity<ErrorResponse> handleCacheEmptyException(
      CacheEmptyException e, HttpServletRequest request) {

    ErrorResponse errorResponse = makeErrorResponse(request, e);

    return new ResponseEntity<>(errorResponse, HttpStatus.SERVICE_UNAVAILABLE);
  }

  @ExceptionHandler(NoSuchCurrencyExistsException.class)
  public ResponseEntity<ErrorResponse> handleNoSuchCurrencyExistsException(
      NoSuchCurrencyExistsException e, HttpServletRequest request) {

    ErrorResponse errorResponse = makeErrorResponse(request, e);

    return new ResponseEntity<>(errorResponse, HttpStatus.NO_CONTENT);
  }

}
