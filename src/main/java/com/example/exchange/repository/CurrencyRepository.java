package com.example.exchange.repository;

import com.example.exchange.entity.Currency;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.List;

public interface CurrencyRepository extends JpaRepository<Currency, Integer> {

    @Override
    List<Currency> findAll();
}
