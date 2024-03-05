package ua.tonkoshkur.currency.exchange.rate.service;

import ua.tonkoshkur.currency.exchange.exception.AlreadyExistsException;
import ua.tonkoshkur.currency.exchange.exception.NotFoundException;
import ua.tonkoshkur.currency.exchange.rate.dto.ExchangeRateDto;
import ua.tonkoshkur.currency.exchange.rate.dto.SaveExchangeRateRequest;
import ua.tonkoshkur.currency.exchange.rate.dto.UpdateExchangeRateRequest;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface ExchangeRateService {

    List<ExchangeRateDto> findAll();

    ExchangeRateDto findByCurrencyCodes(String baseCurrencyCode, String targetCurrencyCode) throws NotFoundException;

    Optional<BigDecimal> getRateByCurrencyIds(int baseCurrencyId, int targetCurrencyId);

    ExchangeRateDto save(SaveExchangeRateRequest saveRequest) throws AlreadyExistsException;

    ExchangeRateDto updateRateByCurrencyCodes(UpdateExchangeRateRequest updateRequest) throws NotFoundException;

}
