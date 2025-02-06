package com.example.exchange.integration;


import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import com.example.exchange.controller.CurrencyController;
import com.example.exchange.dto.CurrencyDto;
import com.example.exchange.entity.Currency;
import com.example.exchange.exception.CurrencyAlreadyExistException;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;


public class CurrencyIntegrationTest extends BaseIntegrationTest {

  @Autowired
  private CurrencyController currencyController;
  @Autowired
  private MockMvc mvc;

  @Test
  public void testGetAllCurrencies() throws Exception {

    currencyRepository.save(Currency.builder().name("EUR").build());
    currencyRepository.save(Currency.builder().name("USD").build());
    currencyRepository.save(Currency.builder().name("YEN").build());

    List<CurrencyDto> currencies = currencyController.getAllCurrency();

    assertThat(currencies.size()).isEqualTo(3);
    assertThat(currencies.get(0).currency()).isEqualTo("EUR");
    assertThat(currencies.get(1).currency()).isEqualTo("USD");
    assertThat(currencies.get(2).currency()).isEqualTo("YEN");

  }


  @Test
  public void testAddCurrency() throws Exception {

    currencyController.addCurrency("EUR");

    List<Currency> currencies = currencyRepository.findAll();

    assertThat(currencies.size()).isEqualTo(1);
    assertThat(currencies.getFirst().getName()).isEqualTo("EUR");

  }

  @Test
  void testAddCurrencyValidationConstraintsInvalidCurrency() throws Exception {

    mvc.perform(post("/api/v1/currencies").param("currency", "US"))
        .andExpect(MockMvcResultMatchers.status().isBadRequest());

  }

  @Test
  void testAddCurrencyValidationConstraintsValidCurrency() throws Exception {

    mvc.perform(post("/api/v1/currencies").param("currency", "USD"))
        .andExpect(MockMvcResultMatchers.status().isCreated());

  }

  @Test
  void testAddCurrencyWhenCurrencyAlreadyExists() throws Exception {
    doThrow(new CurrencyAlreadyExistException("USD")).when(currencyService)
        .addCurrency(new CurrencyDto("EUR"));

    mvc.perform(post("/api/v1/currencies").param("currency", "EUR"))
        .andExpect(MockMvcResultMatchers.status().isConflict());

  }


}
