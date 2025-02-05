package com.example.exchange.integration;


import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import com.example.exchange.controller.CurrencyController;
import com.example.exchange.dto.CurrencyDto;
import com.example.exchange.entity.Currency;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;


public class CurrencyIntegrationTest extends BaseIntegrationTest {

  @Autowired
  private CurrencyController currencyController;

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

    System.out.println(currencies);

    assertThat(currencies.getFirst().getName()).isEqualTo("EUR");

  }

}
