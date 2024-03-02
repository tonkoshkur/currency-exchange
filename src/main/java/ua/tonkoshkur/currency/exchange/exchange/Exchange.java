package ua.tonkoshkur.currency.exchange.exchange;

import ua.tonkoshkur.currency.exchange.currency.Currency;

import java.math.BigDecimal;

public record Exchange(Currency baseCurrency,
                       Currency targetCurrency,
                       BigDecimal rate,
                       BigDecimal amount,
                       BigDecimal convertedAmount) {
}
