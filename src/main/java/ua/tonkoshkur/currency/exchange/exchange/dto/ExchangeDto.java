package ua.tonkoshkur.currency.exchange.exchange.dto;

import ua.tonkoshkur.currency.exchange.currency.dto.CurrencyDto;

import java.math.BigDecimal;

public record ExchangeDto(CurrencyDto baseCurrency,
                          CurrencyDto targetCurrency,
                          BigDecimal rate,
                          BigDecimal amount,
                          BigDecimal convertedAmount) {
}
