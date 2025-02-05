package com.example.exchange.integration;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import com.example.exchange.dto.CurrencyDto;
import com.example.exchange.entity.Currency;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;


public class CurrencyIntegrationTest extends BaseIntegrationTest {

  @Autowired
  private ObjectMapper objectMapper;

  @Test
  public void testGetAllCurrencies() throws Exception {

    currencyRepository.save(Currency.builder().name("EUR").build());
    currencyRepository.save(Currency.builder().name("USD").build());
    currencyRepository.save(Currency.builder().name("YEN").build());

    MvcResult mvcResult = mockMvc.perform(get("/api/v1/currencies")
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(MockMvcResultMatchers.status().isOk()).andReturn();

    String responseBody = mvcResult.getResponse().getContentAsString();
    List<CurrencyDto> currencies = objectMapper.readValue(responseBody,
        objectMapper.getTypeFactory().constructCollectionType(List.class, CurrencyDto.class));

    assert currencies.size() == 3;
    assert "EUR".equals(currencies.get(0).currency());
    assert "USD".equals(currencies.get(1).currency());
    assert "YEN".equals(currencies.get(2).currency());

  }

  @Test
  public void testAddCurrency() throws Exception {

    List<String> currenciesForPost = List.of("EUR", "USD", "CNY", "YEN");

    currenciesForPost.stream().forEach(currency -> {
      try {
        mockMvc.perform(post("/api/v1/currencies")
                .param("currency", currency))
            .andExpect(MockMvcResultMatchers.status().isCreated());
      } catch (Exception e) {
        throw new RuntimeException(e);
      }
    });

    List<Currency> currencies = currencyRepository.findAll();

    System.out.println(currencies);

    assert currencies.size() == 4;
    assert "EUR".equals(currencies.getFirst().getName());


  }



}
