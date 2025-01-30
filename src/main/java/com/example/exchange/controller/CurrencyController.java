package com.example.exchange.controller;

import com.example.exchange.dto.CurrencyDto;
import com.example.exchange.service.CurrencyService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/v1")
@AllArgsConstructor
public class CurrencyController {

  private final CurrencyService currencyService;

  @GetMapping("/currencies")
  @ResponseStatus(HttpStatus.OK)
  @Operation(summary = "Get all currencies",
      description = "This API endpoint returns all currencies.")
  @ApiResponse(responseCode = "200",
      description = "Successful response, returns list of currencies")
  public List<CurrencyDto> getAllCurrency() {
    return currencyService.getAllCurrencies();
  }

}
