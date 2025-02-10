package com.example.exchange.integration;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.example.exchange.controller.ExchangeRatesController;
import com.example.exchange.exception.NoSuchCurrencyExistsException;
import com.example.exchange.service.ExchangeRatesCache;
import com.github.dockerjava.api.exception.NotFoundException;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

class ExchangeRatesControllerIntegrationalTest extends BaseIntegrationTest {

  private static final String VALID_CURRENCY_EUR = "EUR";
  private static final String VALID_CURRENCY_USD = "USD";

  private static final BigDecimal VALID_AMOUNT = BigDecimal.TEN;

  @Autowired
  ExchangeRatesCache exchangeRatesCache;
  @Autowired
  private ExchangeRatesController exchangeRatesController;

  @Test
  public void testGetCurrencyExchangeRates() throws Exception {

    exchangeRatesCache.putExchangeRates(VALID_CURRENCY_EUR,
        new HashMap<>(Map.of(VALID_CURRENCY_USD, BigDecimal.valueOf(10))));

    var currencyRates = exchangeRatesController.getCurrencyExchangeRates(VALID_CURRENCY_EUR,
        VALID_AMOUNT);

    assertThat(currencyRates.size()).isEqualTo(1);
    assertThat(currencyRates.get(VALID_CURRENCY_USD)).isEqualTo(BigDecimal.valueOf(100));

  }
  @Test
  public void testGetCurrencyExchangeRatesInvalidCurrency() throws Exception {
    exchangeRatesCache.putExchangeRates(VALID_CURRENCY_EUR,
        new HashMap<>(Map.of(VALID_CURRENCY_USD, BigDecimal.valueOf(10))));

    assertThrows(NoSuchCurrencyExistsException.class, () -> {
      exchangeRatesController.getCurrencyExchangeRates("USD", VALID_AMOUNT);
    });
  }


}