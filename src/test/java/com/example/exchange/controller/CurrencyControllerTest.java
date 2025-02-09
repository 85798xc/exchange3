package com.example.exchange.controller;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.exchange.dto.CurrencyDto;
import com.example.exchange.service.CurrencyService;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@WebMvcTest(CurrencyController.class)
public class CurrencyControllerTest {

  private static final String VALID_CURRENCY_EUR = "EUR";

  @MockitoBean
  CurrencyService currencyService;
  @Autowired
  private MockMvc mvc;
  @Autowired
  private ObjectMapper objectMapper;

  @Test
  void performGetShouldReturnOk() throws Exception {

    List<CurrencyDto> mockCurrencies = List.of(new CurrencyDto(VALID_CURRENCY_EUR));

    when(currencyService.getAllCurrencies()).thenReturn(mockCurrencies);

    MvcResult mvcResult = mvc.perform(
            get("/api/v1/currencies").contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk()).andReturn();

    String responseBody = mvcResult.getResponse().getContentAsString();
    List<CurrencyDto> currencies = objectMapper.readValue(responseBody,
        objectMapper.getTypeFactory().constructCollectionType(List.class, CurrencyDto.class));

    assertThat(currencies.size()).isEqualTo(1);
    assertThat(currencies.getFirst().currency()).isEqualTo(VALID_CURRENCY_EUR);


  }

  @Test
  void performPostShouldReturnCreated() throws Exception {

    CurrencyDto mockCurrency = new CurrencyDto(VALID_CURRENCY_EUR);
    when(currencyService.addCurrency(any(CurrencyDto.class))).thenReturn(mockCurrency);

    MvcResult mvcResult = mvc.perform(
            post("/api/v1/currencies").param("currency", VALID_CURRENCY_EUR)
                .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isCreated())
        .andReturn();

    String responseBody = mvcResult.getResponse().getContentAsString();
    CurrencyDto responseCurrency = objectMapper.readValue(responseBody, CurrencyDto.class);

    assertThat(responseCurrency.currency()).isEqualTo(VALID_CURRENCY_EUR);
  }

  @Test
  void testAddCurrencyValidationConstraintsInvalidCurrency() throws Exception {

    mvc.perform(post("/api/v1/currencies").param("currency", "US"))
        .andExpect(MockMvcResultMatchers.status().isBadRequest());

  }

  @Test
  void testAddCurrencyValidationConstraintsValidCurrency() throws Exception {

    mvc.perform(post("/api/v1/currencies").param("currency", VALID_CURRENCY_EUR))
        .andExpect(MockMvcResultMatchers.status().isCreated());

  }

}


