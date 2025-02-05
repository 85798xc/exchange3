package com.example.exchange.exception;

import java.time.Instant;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ErrorResponse {

  private String error;
  private String message;
  private Instant timestamp;
  private int statusCode;
}
