package com.example.exchange.mapper;

import com.example.exchange.dto.CurrencyDto;
import com.example.exchange.entity.Currency;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;

@Service
@Getter
@Setter
public class CurrencyMapper {

    public CurrencyDto toDto(Currency currency) {
        return new CurrencyDto(currency.getCurrency());
    }

    public Currency toEntity(CurrencyDto currencyDto) {
        return new Currency(currencyDto.currency());
    }

}
