package com.example.exchange.provider.response;

import com.example.exchange.dto.ApiRequestLogDto;

public record ExchangeRatesResponseWithMetadata(ApiRequestLogDto apiRequestLogDto,
                                                ExchangeRatesApiResponse response) {

}
