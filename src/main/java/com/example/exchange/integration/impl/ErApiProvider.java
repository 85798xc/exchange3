package com.example.exchange.integration.impl;

import com.example.exchange.dto.ApiRequestLogDto;
import com.example.exchange.integration.ExchangeRatesApiProvider;
import com.example.exchange.integration.ExchangeRatesApiResponse;
import com.example.exchange.integration.ExchangeRatesResponseWithMetadata;
import java.time.Instant;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class ErApiProvider implements ExchangeRatesApiProvider {

  private final RestTemplate restTemplate = new RestTemplate();

  @Value("${exchangerates.api.url}")
  private String erApiUrl;

  @Value("${exchangerates.api.key}")
  private String erApiKey;

  @Override
  public ExchangeRatesResponseWithMetadata getExchangeRates(String currencyCodes) {
    String url = erApiUrl + "?access_key=" + erApiKey + "&symbols=" + currencyCodes;

    var response = restTemplate.getForObject(url, ExchangeRatesApiResponse.class);

    if (response != null && response.success()) {
      return new ExchangeRatesResponseWithMetadata(
          new ApiRequestLogDto(response.toString(),url,Instant.now()),
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
