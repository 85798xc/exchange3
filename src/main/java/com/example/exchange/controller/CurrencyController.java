package com.example.exchange.controller;

import com.example.exchange.dto.CurrencyDto;
import com.example.exchange.entity.Currency;
import com.example.exchange.service.CurrencyService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.constraints.Pattern;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;


@RestController
@RequestMapping("/api/v1")
@AllArgsConstructor
@Validated
public class CurrencyController {

  private final CurrencyService currencyService;

  @GetMapping("/currencies")
  @ResponseStatus(HttpStatus.OK)
  @Operation(summary = "Get all currencies",
      description = "This API endpoint returns all currencies.")
  @ApiResponse(responseCode = "200",
      description = "Successful response, returns list of currencies",
      content = @Content(mediaType = "application/json"))
  public List<CurrencyDto> getAllCurrency() {
    return currencyService.getAllCurrencies();
  }


  @PostMapping("/currencies")
  @ResponseStatus(HttpStatus.CREATED)
  @Operation(summary = "Add a new currency",
      description = "This Api endpoint adds a new currency to the system.")
  @ApiResponse(responseCode = "201",
      description = "Currency added successfully",
      content = @Content(mediaType = "application/json"))
  @ApiResponse(responseCode = "400",
      description = "Invalid currency provided")
  public CurrencyDto addCurrency(
      @RequestParam("currency") @Pattern(regexp = "[A-Z]{3}",
          message = "Currency must be provided in format of 3 CAPITAL letters")
      String currency) {
    System.out.println(currency);
    return currencyService.addCurrency(new CurrencyDto(currency));
  }
}
