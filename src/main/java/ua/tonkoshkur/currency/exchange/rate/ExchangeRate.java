package ua.tonkoshkur.currency.exchange.rate;

import ua.tonkoshkur.currency.exchange.currency.Currency;

import java.math.BigDecimal;

public record ExchangeRate(Integer id,
                           Currency baseCurrency,
                           Currency targetCurrency,
                           BigDecimal rate) {
}
