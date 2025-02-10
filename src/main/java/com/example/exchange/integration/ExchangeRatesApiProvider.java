package com.example.exchange.integration;

import java.math.BigDecimal;
import java.util.Map;

public interface ExchangeRatesApiProvider {
  ExchangeRatesResponseWithMetadata getExchangeRates(String currencyCodes);
}
