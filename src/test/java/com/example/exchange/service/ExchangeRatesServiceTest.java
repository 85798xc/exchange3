package com.example.exchange.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ExchangeRatesServiceTest {

  private ExchangeRatesCache mockCache;
  private ExchangeRatesService exchangeRateService;

  @BeforeEach
  void setUp() {
    mockCache = mock(ExchangeRatesCache.class);
    exchangeRateService = new ExchangeRatesService(mockCache);
  }

  @Test
  void testGetExchangeRates_validData() {

    Map<String, BigDecimal> mockRates = new HashMap<>();
    mockRates.put("USD", new BigDecimal("1.10"));
    mockRates.put("EUR", new BigDecimal("0.90"));

    when(mockCache.getExchangeRates("GBP")).thenReturn(mockRates);

    BigDecimal amount = new BigDecimal("2.0");
    Map<String, BigDecimal> result = exchangeRateService.getExchangeRates("GBP", amount);

    assertEquals(new BigDecimal("2.200"), result.get("USD"));
    assertEquals(new BigDecimal("1.800"), result.get("EUR"));

    verify(mockCache).getExchangeRates("GBP");
  }


}
