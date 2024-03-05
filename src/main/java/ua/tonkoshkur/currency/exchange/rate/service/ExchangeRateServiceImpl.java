package ua.tonkoshkur.currency.exchange.rate.service;

import ua.tonkoshkur.currency.exchange.currency.dto.CurrencyDto;
import ua.tonkoshkur.currency.exchange.currency.service.CurrencyService;
import ua.tonkoshkur.currency.exchange.exception.AlreadyExistsException;
import ua.tonkoshkur.currency.exchange.exception.NotFoundException;
import ua.tonkoshkur.currency.exchange.rate.dao.ExchangeRateDao;
import ua.tonkoshkur.currency.exchange.rate.dao.entity.ExchangeRate;
import ua.tonkoshkur.currency.exchange.rate.dto.ExchangeRateDto;
import ua.tonkoshkur.currency.exchange.rate.dto.SaveExchangeRateRequest;
import ua.tonkoshkur.currency.exchange.rate.dto.UpdateExchangeRateRequest;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ExchangeRateServiceImpl implements ExchangeRateService {

    private final ExchangeRateDao exchangeRateDao;

    private final CurrencyService currencyService;

    public ExchangeRateServiceImpl(ExchangeRateDao exchangeRateDao,
                                   CurrencyService currencyService) {
        this.exchangeRateDao = exchangeRateDao;
        this.currencyService = currencyService;
    }

    @Override
    public List<ExchangeRateDto> findAll() {
        List<ExchangeRate> exchangeRateEntities = exchangeRateDao.findAll();

        Set<Integer> currencyIdSet = exchangeRateEntities.stream()
                .flatMap(entity -> Stream.of(entity.baseCurrencyId(), entity.targetCurrencyId()))
                .collect(Collectors.toSet());

        Map<Integer, CurrencyDto> currencyMap = currencyService.findMapByIds(currencyIdSet);

        return exchangeRateEntities.stream()
                .map(entity -> new ExchangeRateDto(
                        entity.id(),
                        currencyMap.get(entity.baseCurrencyId()),
                        currencyMap.get(entity.targetCurrencyId()),
                        entity.rate()))
                .toList();
    }

    @Override
    public ExchangeRateDto findByCurrencyCodes(String baseCurrencyCode, String targetCurrencyCode)
            throws NotFoundException {

        CurrencyDto baseCurrency = currencyService.findByCode(baseCurrencyCode);
        CurrencyDto targetCurrency = currencyService.findByCode(targetCurrencyCode);

        return findByCurrencies(baseCurrency, targetCurrency);
    }

    @Override
    public Optional<BigDecimal> getRateByCurrencyIds(int baseCurrencyId, int targetCurrencyId) {
        return exchangeRateDao.findByBaseAndTargetCurrencyIds(baseCurrencyId, targetCurrencyId)
                .map(ExchangeRate::rate);
    }

    @Override
    public ExchangeRateDto save(SaveExchangeRateRequest saveRequest)
            throws AlreadyExistsException {

        CurrencyDto baseCurrency = currencyService.findByCode(saveRequest.baseCurrencyCode());
        CurrencyDto targetCurrency = currencyService.findByCode(saveRequest.targetCurrencyCode());

        ExchangeRate saveExchangeRate = new ExchangeRate(baseCurrency.id(), targetCurrency.id(), saveRequest.rate());

        exchangeRateDao.save(saveExchangeRate);

        return findByCurrencies(baseCurrency, targetCurrency);
    }

    @Override
    public ExchangeRateDto updateRateByCurrencyCodes(UpdateExchangeRateRequest updateRequest)
            throws NotFoundException {

        ExchangeRateDto existedExchangeRate = findByCurrencyCodes(updateRequest.baseCurrencyCode(),
                updateRequest.targetCurrencyCode());

        exchangeRateDao.updateRateById(existedExchangeRate.id(), updateRequest.rate());

        CurrencyDto baseCurrency = existedExchangeRate.baseCurrency();
        CurrencyDto targetCurrency = existedExchangeRate.targetCurrency();

        return findByCurrencies(baseCurrency, targetCurrency);
    }

    private ExchangeRateDto findByCurrencies(CurrencyDto baseCurrency, CurrencyDto targetCurrency)
            throws NotFoundException {

        return exchangeRateDao.findByBaseAndTargetCurrencyIds(baseCurrency.id(), targetCurrency.id())
                .map(entity -> new ExchangeRateDto(
                        entity.id(),
                        baseCurrency,
                        targetCurrency,
                        entity.rate()))
                .orElseThrow(() -> new NotFoundException(
                        "Exchange rate not found for " + baseCurrency.code() + targetCurrency.code()));
    }
}
