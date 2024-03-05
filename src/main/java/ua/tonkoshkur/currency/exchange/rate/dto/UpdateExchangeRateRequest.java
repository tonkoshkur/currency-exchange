package ua.tonkoshkur.currency.exchange.rate.dto;

import java.math.BigDecimal;

public record UpdateExchangeRateRequest(String baseCurrencyCode,
                                        String targetCurrencyCode,
                                        BigDecimal rate) {
}
