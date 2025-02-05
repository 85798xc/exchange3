package com.example.exchange.controller;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

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

  @MockitoBean
  CurrencyService currencyService;
  @Autowired
  private MockMvc mockMvc;
  @Autowired
  private ObjectMapper objectMapper;

  @Test
  void performGetShouldReturnOk() throws Exception {

    List<CurrencyDto> mockCurrencies = List.of(
        new CurrencyDto("USD"),
        new CurrencyDto("EUR"),
        new CurrencyDto("JPY")
    );

    when(currencyService.getAllCurrencies()).thenReturn(mockCurrencies);

    MvcResult mvcResult = mockMvc.perform(get("/api/v1/currencies")
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(MockMvcResultMatchers.status().isOk()).andReturn();

    String responseBody = mvcResult.getResponse().getContentAsString();
    List<CurrencyDto> currencies = objectMapper.readValue(responseBody,
        objectMapper.getTypeFactory().constructCollectionType(List.class, CurrencyDto.class));

    assertThat(currencies.size()).isEqualTo(3);
    assertThat(currencies.get(0).currency()).isEqualTo("USD");
    assertThat(currencies.get(1).currency()).isEqualTo("EUR");
    assertThat(currencies.get(2).currency()).isEqualTo("JPY");


  }

}


