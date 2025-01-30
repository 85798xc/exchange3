package com.example.exchange.mapper;

import com.example.exchange.dto.CurrencyDto;
import com.example.exchange.entity.Currency;
import org.springframework.stereotype.Service;

@Service
public class CurrencyMapper {

  public CurrencyDto toDto(Currency currency) {
    return new CurrencyDto(currency.getName());
  }

  public Currency toEntity(CurrencyDto currencyDto) {
    return Currency.builder().name(currencyDto.currency()).build();
  }

}
