package com.example.exchange.integration;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.exchange.controller.ExchangeRatesController;
import com.example.exchange.service.ExchangeRatesService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;

class ExchangeRatesControllerIntegrationalTest extends BaseIntegrationTest {

  private static final String VALID_CURRENCY_EUR = "EUR";
  private static final String INVALID_CURRENCY_EU = "EU";

  private static final String VALID_AMOUNT = "1";
  private static final String INVALID_AMOUNT = "0.009";

  @Autowired
  private ExchangeRatesController exchangeRatesController;
  @Autowired
  private MockMvc mvc;
  @Autowired
  private ExchangeRatesService exchangeRatesService;

  @Test
  void getExchangeRatesCurrencyCodeInvalidAmountValid()
      throws Exception {
    mockMvc.perform(get("/api/v1/exchange-rates")
            .param("currency", INVALID_CURRENCY_EU)
            .param("amount", VALID_AMOUNT))
        .andExpect(status().isBadRequest());
  }

  @Test
  void getExchangeRatesCurrencyCodeValidAmountInvalid()
      throws Exception {
    mockMvc.perform(get("/api/v1/exchange-rates")
            .param("currency", VALID_CURRENCY_EUR)
            .param("amount", INVALID_AMOUNT))
        .andExpect(status().isBadRequest());
  }

  @Test
  void getExchangeRatesCurrencyCodeInvalidAmountInvalid()
      throws Exception {
    mockMvc.perform(get("/api/v1/exchange-rates")
            .param("currency", INVALID_CURRENCY_EU)
            .param("amount", INVALID_AMOUNT))
        .andExpect(status().isBadRequest());
  }

  @Test
  void getExchangeRatesWhencacheIsEmpty() throws Exception {

    mockMvc.perform(get("/api/v1/exchange-rates")
            .param("currency", VALID_CURRENCY_EUR)
            .param("amount", VALID_AMOUNT))
        .andExpect(status().isServiceUnavailable());


  }
}