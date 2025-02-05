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
  public ResponseEntity<ErrorResponse> handleConstraintViolationException(ConstraintViolationException e) {
    String errorMessage = e.getMessage();
    ErrorResponse errorResponse = new ErrorResponse(
        "Validation Error",
        e.getMessage(),
        Instant.now(),
        HttpStatus.BAD_REQUEST.value()
    );
    return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(CurrencyAlreadyExistException.class)
  public ResponseEntity<ErrorResponse> handleCurrencyAlreadyExistsException(
      CurrencyAlreadyExistException ex,
      HttpServletRequest request) {

    ErrorResponse errorResponse = new ErrorResponse(
        request.getRequestURI(),
        ex.getMessage(),
        Instant.now(),
        HttpStatus.CONFLICT.value()

    );

    return new ResponseEntity<>(errorResponse, HttpStatus.CONFLICT);
  }












}
