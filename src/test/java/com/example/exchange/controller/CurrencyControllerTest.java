package com.example.exchange.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import com.example.exchange.service.CurrencyService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@WebMvcTest(CurrencyController.class)
public class CurrencyControllerTest {

  @MockitoBean
  CurrencyService currencyService;
  @Autowired
  private MockMvc mvc;

  @Test
  void performGetShouldReturnOk() throws Exception {
    mvc.perform(get("/api/v1/currencies")
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(MockMvcResultMatchers.status().isOk());
  }


}




