package com.example.exchange.integration.impl;

import com.example.exchange.dto.ApiRequestLogDto;
import com.example.exchange.dto.CurrencyDto;
import com.example.exchange.mapper.ApiRequestLogMapper;
import com.example.exchange.repository.ApiRequestLogRepository;
import com.example.exchange.service.CurrencyService;
import com.example.exchange.service.ExchangeRatesCache;
import jakarta.annotation.PostConstruct;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ExchangeRatesApiService {

  private final FixerApiProvider fixerApiProvider;
  private final ErApiProvider erApiProvider;
  private final CurrencyService currencyService;
  private final ExchangeRatesCache exchangeRatesCache;
  private final ApiRequestLogRepository apiRequestLogRepository;
  private final ApiRequestLogMapper apiRequestLogsMapper;

  @PostConstruct
  public void processExchangeRates() {
    var exchangeRatesFromApis = getExchangeRatesFromApisAndSaveLogs();
    var bestExchangeRates = calculateBestRates(exchangeRatesFromApis);
    saveRates(bestExchangeRates);
  }

  private List<Map<String, BigDecimal>> getExchangeRatesFromApisAndSaveLogs() {
    String currencyCodes =
        currencyService.getAllCurrencies().stream()
            .map(CurrencyDto::currency)
            .collect(Collectors.joining(","));

    var responses = List.of(
        fixerApiProvider.getExchangeRates(currencyCodes),
        erApiProvider.getExchangeRates(currencyCodes)
    );

    ArrayList<Map<String, BigDecimal>> ratesFromApis = new ArrayList<>();

    responses.forEach(response -> {
      saveLogs(response.apiRequestLogDto());
      ratesFromApis.add(response.response().rates());
    });

    return ratesFromApis;
  }

  private Map<String, Map<String, BigDecimal>> calculateBestRates(
      List<Map<String, BigDecimal>> exchangeRatesFromApis
  ) {
    var currencies = exchangeRatesFromApis.getFirst().keySet();

    return currencies.stream()
        .collect(Collectors.toMap(
            fromCurrency -> fromCurrency,
            fromCurrency -> currencies.stream()
                .filter(toCurrency -> !toCurrency.equals(fromCurrency))
                .collect(Collectors.toMap(
                    toCurrency -> toCurrency,
                    toCurrency -> {
                      List<BigDecimal> rates = exchangeRatesFromApis.stream()
                          .map(provider -> calculateRate(provider, fromCurrency, toCurrency))
                          .filter(provider -> !provider.equals(BigDecimal.ONE))
                          .collect(Collectors.toList());

                      return compareRates(rates);
                    }
                ))
        ));
  }

  private BigDecimal calculateRate(
      Map<String, BigDecimal> rates, String fromCurrency, String toCurrency
  ) {
    return rates.get(toCurrency).divide(rates.get(fromCurrency), 10, RoundingMode.HALF_UP)
        .stripTrailingZeros();
  }

  private BigDecimal compareRates(List<BigDecimal> rates) {
    return rates.stream().max(BigDecimal::compareTo).orElse(BigDecimal.ZERO).stripTrailingZeros();
  }

  private void saveRates(Map<String, Map<String, BigDecimal>> rates) {
    exchangeRatesCache.putExchangeRates(rates);
  }

  private void saveLogs(ApiRequestLogDto apiRequestLogDto) {
    apiRequestLogRepository.save(apiRequestLogsMapper.toApiRequestLog(apiRequestLogDto));
  }
}
