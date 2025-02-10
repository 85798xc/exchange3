package com.example.exchange.config;

import com.example.exchange.service.ExchangeRatesApiService;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ExchangeRatesScheduler {

  private final ExchangeRatesApiService exchangeRatesApiService;

  @PostConstruct
  public void init() {
    processExchangeRatesOnStartAndEveryHourAfter();
  }

  @Scheduled(fixedRate = 3600000)
  public void processExchangeRatesOnStartAndEveryHourAfter() {
    exchangeRatesApiService.processExchangeRates();
  }
}
