package ua.tonkoshkur.currency.exchange.exchange.service;

import ua.tonkoshkur.currency.exchange.currency.dto.CurrencyDto;
import ua.tonkoshkur.currency.exchange.currency.service.CurrencyService;
import ua.tonkoshkur.currency.exchange.exception.NotFoundException;
import ua.tonkoshkur.currency.exchange.exchange.dto.ExchangeDto;
import ua.tonkoshkur.currency.exchange.rate.service.ExchangeRateService;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Optional;

public class ExchangeServiceImpl implements ExchangeService {

    private final CurrencyService currencyService;
    private final ExchangeRateService exchangeRateService;
    private final String defaultCurrencyCode;
    private final int convertedAmountDecimalPlaces;

    public ExchangeServiceImpl(CurrencyService currencyService,
                               ExchangeRateService exchangeRateService,
                               String defaultCurrencyCode,
                               int convertedAmountDecimalPlaces) {
        this.currencyService = currencyService;
        this.exchangeRateService = exchangeRateService;
        this.defaultCurrencyCode = defaultCurrencyCode;
        this.convertedAmountDecimalPlaces = convertedAmountDecimalPlaces;
    }

    @Override
    public ExchangeDto calculate(String baseCurrencyCode, String targetCurrencyCode, BigDecimal amount) {
        CurrencyDto baseCurrency = currencyService.findByCode(baseCurrencyCode);
        CurrencyDto targetCurrency = currencyService.findByCode(targetCurrencyCode);

        BigDecimal rate = getRate(baseCurrency.id(), targetCurrency.id())
                .orElseThrow(() -> new NotFoundException(String.format("Exchange rate not found for %s and %s", baseCurrencyCode, targetCurrencyCode)));

        BigDecimal convertedAmount = amount.multiply(rate);

        return new ExchangeDto(baseCurrency, targetCurrency, rate, amount, convertedAmount);
    }

    private Optional<BigDecimal> getRate(int baseCurrencyId, int targetCurrencyId) {
        return exchangeRateService.getRateByCurrencyIds(baseCurrencyId, targetCurrencyId)
                .or(() -> getReverseRate(baseCurrencyId, targetCurrencyId))
                .or(() -> getRateUsingDefaultCurrency(baseCurrencyId, targetCurrencyId));
    }

    private Optional<BigDecimal> getReverseRate(int baseCurrencyId, int targetCurrencyId) {
        return exchangeRateService.getRateByCurrencyIds(targetCurrencyId, baseCurrencyId)
                .map(rate -> new BigDecimal(1).divide(rate, convertedAmountDecimalPlaces, RoundingMode.HALF_UP));
    }

    private Optional<BigDecimal> getRateUsingDefaultCurrency(int baseCurrencyId, int targetCurrencyId) {
        int defaultCurrencyId = currencyService.findByCode(defaultCurrencyCode).id();

        Optional<BigDecimal> defaultBaseRateOptional = exchangeRateService.getRateByCurrencyIds(defaultCurrencyId, baseCurrencyId);
        Optional<BigDecimal> defaultTargetRateOptional = exchangeRateService.getRateByCurrencyIds(defaultCurrencyId, targetCurrencyId);

        if (defaultBaseRateOptional.isEmpty() || defaultTargetRateOptional.isEmpty()) {
            return Optional.empty();
        }

        BigDecimal baseDefaultRate = defaultBaseRateOptional.get();
        BigDecimal targetDefaultRate = defaultTargetRateOptional.get();

        BigDecimal rate = targetDefaultRate.divide(baseDefaultRate, convertedAmountDecimalPlaces, RoundingMode.HALF_UP);
        return Optional.of(rate);
    }

}
