package com.example.exchange.controller;

import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import com.example.exchange.dto.CurrencyDto;
import com.example.exchange.exception.CurrencyAlreadyExistException;
import com.example.exchange.service.CurrencyService;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@WebMvcTest(CurrencyController.class)
public class CurrencyControllerTest {

  @Autowired
  private MockMvc mvc;

  @MockitoBean
  CurrencyService currencyService;

  @Test
  void performGetShouldReturnOk() throws Exception {
    mvc.perform(get("/api/v1/currencies")
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(MockMvcResultMatchers.status().isOk());
  }

  @Test
  void testAddCurrencyValidationConstraintsInvalidCurrency() throws Exception {
    List<String> currenciesForPost = List.of("EU", "US", "CN", "YE");

    currenciesForPost.forEach(currency -> {
      try {
        mvc.perform(post("/api/v1/currencies")
                .param("currency", currency))
            .andExpect(MockMvcResultMatchers.status().isBadRequest());
      } catch (Exception e) {
        throw new RuntimeException(e);
      }
    });


  }

  @Test
  void testAddCurrencyValidationConstraintsValidCurrency() throws Exception {
    List<String> currenciesForPost = List.of("EUR", "USD", "CNY", "YEN");

    currenciesForPost.forEach(currency -> {
      try {
        mvc.perform(post("/api/v1/currencies")
                .param("currency", currency))
            .andExpect(MockMvcResultMatchers.status().isCreated());
      } catch (Exception e) {
        throw new RuntimeException(e);
      }
    });
  }

  @Test
  void testAddCurrencyWhenCurrencyAlreadyExists() throws Exception {
    doThrow(new CurrencyAlreadyExistException("USD")).when(currencyService)
        .addCurrency(new CurrencyDto("EUR"));

        mvc.perform(post("/api/v1/currencies")
                .param("currency", "EUR"))
            .andExpect(MockMvcResultMatchers.status().isConflict());

    }
  }




