package com.example.exchange.service;

import com.example.exchange.dto.CurrencyDto;
import com.example.exchange.entity.Currency;
import com.example.exchange.exception.CurrencyAlreadyExistException;
import com.example.exchange.mapper.CurrencyMapper;
import com.example.exchange.repository.CurrencyRepository;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CurrencyService {

  private final CurrencyRepository currencyRepository;
  private final CurrencyMapper currencyMapper;


  public List<CurrencyDto> getAllCurrencies() {
    return currencyRepository.findAll().stream().map(currencyMapper::toDto).toList();
  }

  public CurrencyDto addCurrency(CurrencyDto currencyDto) {

    Currency existingCurrency = currencyRepository.findByName(currencyDto.currency());

    if (existingCurrency != null) {
      throw new CurrencyAlreadyExistException(currencyDto.currency());
    }

    currencyRepository.save(currencyMapper.toEntity(currencyDto));

    return currencyDto;
  }
}
