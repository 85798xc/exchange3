package com.example.exchange.provider.impl;

import com.example.exchange.dto.ApiRequestLogDto;
import com.example.exchange.provider.ExchangeRatesApiProvider;
import com.example.exchange.provider.response.ExchangeRatesApiResponse;
import com.example.exchange.provider.response.ExchangeRatesResponseWithMetadata;
import java.time.Instant;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
@RequiredArgsConstructor
public class ErApiProvider implements ExchangeRatesApiProvider {

  private final RestTemplate restTemplate;

  @Value("${exchangerates.api.url}")
  private String erApiUrl;

  @Value("${exchangerates.api.key}")
  private String erApiKey;

  @Override
  public ExchangeRatesResponseWithMetadata getExchangeRates(String currencyCodes) {
    String url = erApiUrl + "?access_key=" + erApiKey + "&symbols=" + currencyCodes;

    var response = restTemplate.getForObject(url, ExchangeRatesApiResponse.class);

    return new ExchangeRatesResponseWithMetadata(
        new ApiRequestLogDto(response != null ? response.toString() : "", url, Instant.now()),
        response
    );
  }
}

