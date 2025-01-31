package com.example.exchange.service;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import com.example.exchange.dto.CurrencyDto;
import com.example.exchange.entity.Currency;
import com.example.exchange.mapper.CurrencyMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;


@ExtendWith(SpringExtension.class)
class CurrencyMapperTest {

  CurrencyMapper currencyMapper = new CurrencyMapper();

  @Test
  void toDtoTest() {
    assertThat(currencyMapper.toDto(Currency.builder().name("USD").build())).isEqualTo(
        new CurrencyDto("USD"));
  }

  @Test
  void toEntityTest() {
    assertThat(currencyMapper.toEntity(new CurrencyDto("USD"))).isEqualTo(
        Currency.builder().name("USD").build());
  }
}