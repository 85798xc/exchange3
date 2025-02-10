package com.example.exchange.provider.impl;

import com.example.exchange.dto.ApiRequestLogDto;
import com.example.exchange.provider.ExchangeRatesApiProvider;
import com.example.exchange.provider.response.ExchangeRatesApiResponse;
import com.example.exchange.provider.response.ExchangeRatesResponseWithMetadata;
import java.time.Instant;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class FixerApiProvider implements ExchangeRatesApiProvider {

  private final RestTemplate restTemplate;

  @Value("${fixer.api.url}")
  private String fixerApiUrl;

  @Value("${fixer.api.key}")
  private String fixerApiKey;

  @Override
  public ExchangeRatesResponseWithMetadata getExchangeRates(String currencyCodes) {
    String url = fixerApiUrl + "?access_key=" + fixerApiKey + "&symbols=" + currencyCodes;

    var response = restTemplate.getForObject(url, ExchangeRatesApiResponse.class);

    return new ExchangeRatesResponseWithMetadata(
        new ApiRequestLogDto(response != null ? response.toString() : "", url, Instant.now()),
        response
    );
  }
}

