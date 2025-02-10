package com.example.exchange.service;

import java.math.BigDecimal;
import java.util.Map;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ExchangeRatesService {

  private final ExchangeRatesCache exchangeRatesCache;

  public Map<String, BigDecimal> getExchangeRates(String currency, BigDecimal amount) {
    var exchangeRates = new java.util.HashMap<>(
        Map.copyOf(exchangeRatesCache.getExchangeRates(currency)));

    exchangeRates.replaceAll((key, value) -> value.multiply(amount));

    return exchangeRates;
  }
}
