package com.example.exchange.integration.impl;

import com.example.exchange.dto.ApiRequestLogDto;
import com.example.exchange.integration.ExchangeRatesApiProvider;
import com.example.exchange.integration.ExchangeRatesApiResponse;
import com.example.exchange.integration.ExchangeRatesResponseWithMetadata;
import java.time.Instant;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.client.RestTemplate;

public class OpenExchangeRatesApiProvider implements ExchangeRatesApiProvider {
  private final RestTemplate restTemplate = new RestTemplate();

  @Value("${fixer.api.url}")
  private String openApiUrl;

  @Value("${fixer.api.key}")
  private String openApiKey;

  @Override
  public ExchangeRatesResponseWithMetadata getExchangeRates(String currencyCodes) {
    String url = openApiUrl + "?app_id=" + openApiKey + "&symbols=" + currencyCodes;

    var response = restTemplate.getForObject(url, ExchangeRatesApiResponse.class);

    if (response != null && response.success()) {
      return new ExchangeRatesResponseWithMetadata(
          new ApiRequestLogDto(response.toString(), url, Instant.now()),
          response
      );
    } else {
      return new ExchangeRatesResponseWithMetadata(
          new ApiRequestLogDto(response != null ? response.toString() : "", url, Instant.now()),
          response
      );
    }
  }
}
