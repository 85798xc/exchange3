package com.example.exchange.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import com.example.exchange.dto.CurrencyDto;
import com.example.exchange.entity.Currency;
import com.example.exchange.mapper.CurrencyMapper;
import com.example.exchange.repository.CurrencyRepository;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class CurrencyServiceTest {

  private final CurrencyDto mockCurrencyDto1 = new CurrencyDto("USD");
  private final CurrencyDto mockCurrencyDto2 = new CurrencyDto("EUR");
  @Mock
  private CurrencyRepository currencyRepository;
  @Mock
  private CurrencyMapper currencyMapper;
  @InjectMocks
  private CurrencyService currencyService;

  @BeforeEach
  void setUp() {

  }

  @Test
  void testGetAllCurrencies() {

    when(currencyRepository.findAll()).thenReturn(List.of(
        new Currency(1L, "USD"),
        new Currency(2L, "EUR")
    ));

    when(currencyMapper.toDto(new Currency(1L, "USD"))).thenReturn(mockCurrencyDto1);
    when(currencyMapper.toDto(new Currency(2L, "EUR"))).thenReturn(mockCurrencyDto2);

    List<CurrencyDto> result = currencyService.getAllCurrencies();

    assertThat(result.size()).isEqualTo(2);
    assertThat(result.get(0)).isEqualTo(mockCurrencyDto1);
    assertThat(result.get(1)).isEqualTo(mockCurrencyDto2);

  }
}