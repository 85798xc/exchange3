package com.example.exchange.integration;

import com.example.exchange.dto.ApiRequestLogDto;

public record ExchangeRatesResponseWithMetadata(ApiRequestLogDto apiRequestLogDto,
                                                ExchangeRatesApiResponse response) {

}
