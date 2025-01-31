package com.example.exchange.integration;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import com.example.exchange.entity.Currency;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

public class CurrencyIntegrationTest extends BaseIntegrationTest {

  @Test
  public void testGetAllCurrencies() throws Exception {

    currencyRepository.save(Currency.builder().name("EUR").build());
    currencyRepository.save(Currency.builder().name("USD").build());

    mockMvc.perform(get("/api/v1/currencies")
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(
            MockMvcResultMatchers.content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
        .andExpect(MockMvcResultMatchers.jsonPath("$[0].currency").value("EUR"))
        .andExpect(MockMvcResultMatchers.jsonPath("$[1].currency").value("USD"));
  }


}
