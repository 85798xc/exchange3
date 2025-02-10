package com.example.exchange.service;

import java.math.BigDecimal;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ExchangeRatesService {

  private final ExchangeRatesCache exchangeRatesCache;

  public Map<String, BigDecimal> getExchangeRates(String currency, BigDecimal amount) {
    return exchangeRatesCache.getExchangeRates(currency).entrySet().stream()
        .collect(Collectors.toMap(Map.Entry::getKey, entry -> entry.getValue().multiply(amount)));
  }
}
