package ua.tonkoshkur.currency.exchange.rate.dao.entity;

import java.math.BigDecimal;

public record ExchangeRate(Integer id,
                           int baseCurrencyId,
                           int targetCurrencyId,
                           BigDecimal rate) {
}
