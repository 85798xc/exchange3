package com.example.exchange.integration;

import com.example.exchange.repository.CurrencyRepository;
import com.example.exchange.service.CurrencyService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.containers.PostgreSQLContainer;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc(addFilters = false)
public class BaseIntegrationTest {

  @ServiceConnection
  private static final PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:17");
  @Autowired
  public MockMvc mockMvc;
  @Autowired
  protected CurrencyService currencyService;
  @Autowired
  CurrencyRepository currencyRepository;

  @BeforeAll
  static void beforeAll() {
    postgres.start();
  }

  @BeforeEach
  void setUp() {
    currencyRepository.deleteAll();
  }

}
