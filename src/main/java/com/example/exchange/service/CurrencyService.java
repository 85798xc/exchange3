package com.example.exchange.service;

import com.example.exchange.entity.Currency;
import com.example.exchange.repository.CurrencyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CurrencyService {

    private final CurrencyRepository currencyRepository;

    @Autowired
    public CurrencyService(CurrencyRepository currencyRepository) {
        this.currencyRepository = currencyRepository;
    }


    public List<Currency> getAllCurrencies() {
        return currencyRepository.findAll();
    }
}
