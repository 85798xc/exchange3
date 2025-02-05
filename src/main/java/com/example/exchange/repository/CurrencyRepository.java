package com.example.exchange.repository;

import com.example.exchange.entity.Currency;
import org.springframework.data.jpa.repository.JpaRepository;




public interface CurrencyRepository extends JpaRepository<Currency, Integer> {

  Currency findByName(String currency);
}
