package ua.tonkoshkur.currency.exchange.exchange.service;

import ua.tonkoshkur.currency.exchange.exchange.dto.ExchangeDto;

import java.math.BigDecimal;

public interface ExchangeService {

    ExchangeDto calculate(String baseCurrencyCode, String targetCurrencyCode, BigDecimal amount);
}
