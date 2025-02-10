package com.example.exchange.provider;

import com.example.exchange.provider.response.ExchangeRatesResponseWithMetadata;

public interface ExchangeRatesApiProvider {
  ExchangeRatesResponseWithMetadata getExchangeRates(String currencyCodes);
}
