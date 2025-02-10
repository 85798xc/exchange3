package com.example.exchange.exception;

import java.time.Instant;


public record ErrorResponse(String error,
                            String message,
                            Instant timestamp
                            ) {


}
