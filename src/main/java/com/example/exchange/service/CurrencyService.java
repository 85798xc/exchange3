package com.example.exchange.service;

import com.example.exchange.dto.CurrencyDto;
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
        return currencyRepository.findAll().stream()
                .map(currencyMapper::toDto)
                .toList();
    }
}
