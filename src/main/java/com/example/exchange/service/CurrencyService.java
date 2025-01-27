package com.example.exchange.service;

import com.example.exchange.dto.CurrencyDto;
import com.example.exchange.entity.Currency;
import com.example.exchange.mapper.CurrencyMapper;
import com.example.exchange.repository.CurrencyRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CurrencyService {

    private final CurrencyRepository currencyRepository;
    private final CurrencyMapper currencyMapper;


    public List<CurrencyDto> getAllCurrencies() {
        List<Currency> currencies = currencyRepository.findAll();
        return currencies.stream()
                .map(currencyMapper::toDto)
                .collect(Collectors.toList());
    }
}
