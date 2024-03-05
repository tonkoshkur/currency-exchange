package ua.tonkoshkur.currency.exchange.exchange.dto;

import java.math.BigDecimal;

public record CalculateExchangeRequest(String fromCurrencyCode,
                                       String toCurrencyCode,
                                       BigDecimal amount) {
}
