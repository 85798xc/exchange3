package com.example.exchange.integration;

import com.example.exchange.repository.CurrencyRepository;
import com.example.exchange.service.CurrencyService;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.containers.PostgreSQLContainer;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class BaseIntegrationTest {

  @ServiceConnection
  private static final PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:15");

  @Autowired
  CurrencyRepository currencyRepository;

  @MockitoBean
  protected CurrencyService currencyService;

  @Autowired
  public MockMvc mockMvc;

  static void beforeAll() {
    postgres.start();
  }

  @BeforeEach
  void setUp() {
    currencyRepository.deleteAll();
  }

}
