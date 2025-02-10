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
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

@WebMvcTest(ExchangeRatesController.class)
@AutoConfigureMockMvc(addFilters = false)
class ExchangeRatesControllerTest {

  private static final String VALID_CURRENCY_EUR = "EUR";
  private static final BigDecimal VALID_AMMOUNT = BigDecimal.valueOf(1.2);
  private static final String VALID_AMMOUNT_STRING = "1.2";

  @Autowired
  private MockMvc mvc;
  @MockitoBean
  private ExchangeRatesService exchangeRatesService;
  @Autowired
  private ObjectMapper objectMapper;

  @Test
  void performGetShouldReturnOk() throws Exception {

    Map<String, BigDecimal> mockCurrenciesRates = Map.of(VALID_CURRENCY_EUR, VALID_AMMOUNT);

    when(exchangeRatesService.getExchangeRates(any(String.class), any(BigDecimal.class)))
        .thenReturn(mockCurrenciesRates);

    MvcResult mvcResult = mvc.perform(
            get("/api/v1/exchange-rates").param("currency", VALID_CURRENCY_EUR)
                .param("amount", VALID_AMMOUNT_STRING).contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk()).andReturn();

    String responseBody = mvcResult.getResponse().getContentAsString();
    Map<String, BigDecimal> currenciesRates = objectMapper.readValue(responseBody,
        objectMapper.getTypeFactory().constructMapType(Map.class, String.class, BigDecimal.class));
    System.out.println(currenciesRates);
    assertThat(currenciesRates.size()).isEqualTo(1);
    assertThat(currenciesRates.get(VALID_CURRENCY_EUR)).isEqualTo(VALID_AMMOUNT);


  }
}