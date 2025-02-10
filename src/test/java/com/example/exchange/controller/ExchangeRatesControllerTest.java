package com.example.exchange.controller;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.exchange.service.ExchangeRatesService;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.math.BigDecimal;
import java.util.Map;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

@WebMvcTest(ExchangeRatesController.class)
class ExchangeRatesControllerTest {

  private static final String VALID_CURRENCY_EUR = "EUR";
  private static final BigDecimal VALID_AMOUNT = BigDecimal.valueOf(10);
  private static final BigDecimal INVALID_AMOUNT = BigDecimal.valueOf(-1);
  private static final String INVALID_CURRENCY_EU = "EU";
  @Autowired
  private MockMvc mvc;
  @MockitoBean
  private ExchangeRatesService exchangeRatesService;
  @Autowired
  private ObjectMapper objectMapper;

  @Test
  void performGetShouldReturnOk() throws Exception {

    Map<String, BigDecimal> mockCurrenciesRates = Map.of(VALID_CURRENCY_EUR, VALID_AMOUNT);

    when(exchangeRatesService.getExchangeRates(any(String.class), any(BigDecimal.class)))
        .thenReturn(mockCurrenciesRates);

    MvcResult mvcResult = mvc.perform(
            get("/api/v1/exchange-rates").param("currency", VALID_CURRENCY_EUR)
                .param("amount", VALID_AMOUNT.toString()).contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk()).andReturn();

    String responseBody = mvcResult.getResponse().getContentAsString();
    Map<String, BigDecimal> currenciesRates = objectMapper.readValue(responseBody,
        objectMapper.getTypeFactory().constructMapType(Map.class, String.class, BigDecimal.class));
    System.out.println(currenciesRates);
    assertThat(currenciesRates.size()).isEqualTo(1);
    assertThat(currenciesRates.get(VALID_CURRENCY_EUR)).isEqualTo(VALID_AMOUNT);


  }

  @Test
  void getExchangeRatesCurrencyCodeInvalidAmountValid()
      throws Exception {
    mvc.perform(get("/api/v1/exchange-rates")
            .param("currency", INVALID_CURRENCY_EU)
            .param("amount", VALID_AMOUNT.toString()))
        .andExpect(status().isBadRequest());
  }

  @Test
  void getExchangeRatesCurrencyCodeValidAmountInvalid()
      throws Exception {
    mvc.perform(get("/api/v1/exchange-rates")
            .param("currency", VALID_CURRENCY_EUR)
            .param("amount", INVALID_AMOUNT.toString()))
        .andExpect(status().isBadRequest());
  }

  @Test
  void getExchangeRatesCurrencyCodeInvalidAmountInvalid()
      throws Exception {
    mvc.perform(get("/api/v1/exchange-rates")
            .param("currency", INVALID_CURRENCY_EU)
            .param("amount", INVALID_AMOUNT.toString()))
        .andExpect(status().isBadRequest());
  }

  @Test
  void getExchangeRatesWhencacheIsEmpty() throws Exception {

    mvc.perform(get("/api/v1/exchange-rates")
            .param("currency", VALID_CURRENCY_EUR)
            .param("amount", VALID_AMOUNT.toString()))
        .andExpect(status().isServiceUnavailable());


  }
}