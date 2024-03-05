package ua.tonkoshkur.currency.exchange.rate.dto;

import ua.tonkoshkur.currency.exchange.currency.dto.CurrencyDto;

import java.math.BigDecimal;

public record ExchangeRateDto(Integer id,
                              CurrencyDto baseCurrency,
                              CurrencyDto targetCurrency,
                              BigDecimal rate) {
}
