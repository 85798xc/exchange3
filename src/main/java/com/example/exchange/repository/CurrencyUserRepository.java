package com.example.exchange.repository;

import com.example.exchange.entity.CurrencyUser;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CurrencyUserRepository extends JpaRepository<CurrencyUser, Long> {
  Optional<CurrencyUser> findByUsername(String username);
}
