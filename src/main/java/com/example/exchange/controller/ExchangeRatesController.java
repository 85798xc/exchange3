package com.example.exchange.controller;

import com.example.exchange.integration.impl.ExchangeRatesApiService;
import com.example.exchange.service.ExchangeRatesService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Pattern;
import java.math.BigDecimal;
import java.util.Map;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/api/v1")
@AllArgsConstructor
@Validated
public class ExchangeRatesController {

  private final ExchangeRatesService exchangeRatesService;
  private final ExchangeRatesApiService exchangeRatesApiService;

  @GetMapping("/exchange-rates")
  @ResponseStatus(HttpStatus.OK)
  @Operation(summary = "Get  latest exchange rates from cache",
      description = "This API endpoint returns exchange rates.")
  @ApiResponse(responseCode = "200",
      description = "Successful response, returns exchange rates",
      content = @Content(mediaType = "application/json"))
  public Map<String, BigDecimal> getCurrencyExchangeRates(
      @RequestParam("currency")
      @Pattern(
          regexp = "[A-Z]{3}",
          message = "Currency must be provided in format of 3 capital letters"
      ) String currency,
      @RequestParam("amount")
      @DecimalMin(value = "0.01", message = "Amount must be at least 0.1")
      @DecimalMax(value = "1000000000", message = "Amount must not exceed 1,000,000,000")
      BigDecimal amount
  ) {
    return exchangeRatesService.getExchangeRates(currency, amount);
  }


@GetMapping("/get-rates")
  public void getRates() {
    exchangeRatesApiService.processExchangeRates();
  }
}
