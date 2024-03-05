package ua.tonkoshkur.currency.exchange.rate.dto;

import java.math.BigDecimal;

public record SaveExchangeRateRequest(String baseCurrencyCode,
                                      String targetCurrencyCode,
                                      BigDecimal rate) {
}
