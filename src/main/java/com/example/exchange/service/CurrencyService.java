package com.example.exchange.service;

import com.example.exchange.dto.CurrencyDto;
import com.example.exchange.entity.Currency;
import com.example.exchange.exception.CurrencyAlreadyExistException;
import com.example.exchange.mapper.CurrencyMapper;
import com.example.exchange.repository.CurrencyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CurrencyService {

    private final CurrencyRepository currencyRepository;
    private final CurrencyMapper currencyMapper;


    public List<CurrencyDto> getAllCurrencies() {
        return currencyRepository.findAll().stream().map(currencyMapper::toDto).toList();
    }

    public CurrencyDto addCurrency(CurrencyDto currencyDto) {

        if (currencyRepository.existsByName(currencyDto.currency())) {
            throw new CurrencyAlreadyExistException(currencyDto.currency());
        }

        Currency currency = currencyRepository.save(currencyMapper.toEntity(currencyDto));

        return currencyMapper.toDto(currency);
    }
}
