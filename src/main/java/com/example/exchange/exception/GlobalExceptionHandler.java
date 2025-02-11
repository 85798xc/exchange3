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

  private static ResponseEntity<ErrorResponse> makeResponseEntity(HttpServletRequest request,
      Exception e, HttpStatus status) {
    return new ResponseEntity<>(
        new ErrorResponse(request.getRequestURI(), e.getMessage(), Instant.now()),
        HttpStatus.valueOf(status.value()));
  }

  @ExceptionHandler(ConstraintViolationException.class)
  public ResponseEntity<ErrorResponse> handleConstraintViolationException(
      ConstraintViolationException e, HttpServletRequest request) {

    return makeResponseEntity(request, e, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(CurrencyAlreadyExistException.class)
  public ResponseEntity<ErrorResponse> handleCurrencyAlreadyExistsException(
      CurrencyAlreadyExistException e, HttpServletRequest request) {

    return makeResponseEntity(request, e, HttpStatus.CONFLICT);
  }

  @ExceptionHandler(CacheEmptyException.class)
  public ResponseEntity<ErrorResponse> handleCacheEmptyException(
      CacheEmptyException e, HttpServletRequest request) {

    return makeResponseEntity(request, e, HttpStatus.SERVICE_UNAVAILABLE);
  }

  @ExceptionHandler(NoSuchCurrencyExistsException.class)
  public ResponseEntity<ErrorResponse> handleNoSuchCurrencyExistsException(
      NoSuchCurrencyExistsException e, HttpServletRequest request) {

    return makeResponseEntity(request, e, HttpStatus.NO_CONTENT);
  }

}
