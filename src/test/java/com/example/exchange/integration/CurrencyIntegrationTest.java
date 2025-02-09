package com.example.exchange.integration;


import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import com.example.exchange.controller.CurrencyController;
import com.example.exchange.dto.CurrencyDto;
import com.example.exchange.entity.Currency;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;


public class CurrencyIntegrationTest extends BaseIntegrationTest {

  private static final String VALID_CURRENCY_EUR = "EUR";

  @Autowired
  private CurrencyController currencyController;
  @Autowired
  private MockMvc mvc;

  @Test
  public void testGetAllCurrencies() throws Exception {

    currencyRepository.save(Currency.builder().name(VALID_CURRENCY_EUR).build());

    List<CurrencyDto> currencies = currencyController.getAllCurrency();

    assertThat(currencies.size()).isEqualTo(1);
    assertThat(currencies.getFirst().currency()).isEqualTo(VALID_CURRENCY_EUR);


  }


  @Test
  public void testAddCurrency() throws Exception {

    currencyController.addCurrency(VALID_CURRENCY_EUR);

    List<Currency> currencies = currencyRepository.findAll();

    assertThat(currencies.size()).isEqualTo(1);
    assertThat(currencies.getFirst().getName()).isEqualTo(VALID_CURRENCY_EUR);

  }


  @Test
  void testAddCurrencyWhenCurrencyAlreadyExists() throws Exception {
    currencyRepository.save(Currency.builder().name(VALID_CURRENCY_EUR).build());

    mvc.perform(post("/api/v1/currencies").param("currency", VALID_CURRENCY_EUR))
        .andExpect(MockMvcResultMatchers.status().isConflict());

  }


}
