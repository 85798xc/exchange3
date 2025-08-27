package com.example.exchange.repository;

import com.example.exchange.entity.CurrencyUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface CurrencyUserRepository extends JpaRepository<CurrencyUser, Long> {
    Optional<CurrencyUser> findByUsername(String username);
}
