package com.example.exchange.mapper;

import com.example.exchange.dto.ApiRequestLogDto;
import com.example.exchange.entity.ApiRequestLog;
import java.time.Instant;
import org.springframework.stereotype.Component;

@Component
public class ApiRequestLogMapper {

  public ApiRequestLogDto toApiRequestLogDto(ApiRequestLog apiRequestLog) {
    return new ApiRequestLogDto(
        apiRequestLog.getResponse(), apiRequestLog.getUrl(), Instant.now()
    );
  }


  public ApiRequestLog toApiRequestLog(ApiRequestLogDto apiRequestLogDto) {
    return ApiRequestLog.builder()
        .response(apiRequestLogDto.response())
        .url(apiRequestLogDto.url())
        .timestamp(Instant.now()).build();

  }
}
