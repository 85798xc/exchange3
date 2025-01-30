package com.example.exchange.integration;


import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import com.example.exchange.dto.CurrencyDto;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

public class CurrencyIntegrationTest extends BaseIntegrationTest {

  @Test
  public void testGetAllCurrencies() throws Exception {

    CurrencyDto currencyDto1 = new CurrencyDto("USD");
    CurrencyDto currencyDto2 = new CurrencyDto("EUR");
    List<CurrencyDto> currencies = List.of(currencyDto1, currencyDto2);

    when(currencyService.getAllCurrencies()).thenReturn(currencies);

    mockMvc.perform(get("/api/v1/currencies")
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(
            MockMvcResultMatchers.content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
        .andExpect(MockMvcResultMatchers.jsonPath("$[0].currency").value("USD"))
        .andExpect(MockMvcResultMatchers.jsonPath("$[1].currency").value("EUR"));
  }


}
