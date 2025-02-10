package com.example.exchange.service;

import com.example.exchange.exception.CacheEmptyException;
import com.example.exchange.exception.NoSuchCurrencyExistsException;
import java.math.BigDecimal;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.stereotype.Component;

@Component
public class ExchangeRatesCache {
  private final Map<String, Map<String, BigDecimal>> exchangeRates =
      new ConcurrentHashMap<>();

  public Map<String, BigDecimal> getExchangeRates(String currency) {
    if (exchangeRates.isEmpty()) {
      throw new CacheEmptyException();
    }
    var exchangeRatesForConcreteCurrency = exchangeRates.get(currency);
    if (exchangeRatesForConcreteCurrency == null) {
      throw new NoSuchCurrencyExistsException(currency);
    }
    return exchangeRatesForConcreteCurrency;
  }

  public void putExchangeRates(Map<String, Map<String, BigDecimal>> exchangeRatesFromApi) {
    exchangeRates.putAll(exchangeRatesFromApi);
  }

  public void clearCache() {
    exchangeRates.clear();
  }
}
