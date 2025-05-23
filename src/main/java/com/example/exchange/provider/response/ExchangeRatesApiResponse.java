package com.example.exchange.provider.response;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Map;

public record ExchangeRatesApiResponse(boolean success,
                                       Instant timestamp,
                                       String base,
                                       String date,
                                       Map<String, BigDecimal> rates) {

}
