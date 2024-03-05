package ua.tonkoshkur.currency.exchange.rate.service;

import ua.tonkoshkur.currency.exchange.exception.AlreadyExistsException;
import ua.tonkoshkur.currency.exchange.exception.NotFoundException;
import ua.tonkoshkur.currency.exchange.rate.dto.ExchangeRateDto;
import ua.tonkoshkur.currency.exchange.rate.dto.SaveExchangeRateRequest;
import ua.tonkoshkur.currency.exchange.rate.dto.UpdateExchangeRateRequest;

import java.util.List;

public interface ExchangeRateService {

    List<ExchangeRateDto> findAll();

    ExchangeRateDto findByCurrencyCodes(String baseCurrencyCode, String targetCurrencyCode) throws NotFoundException;

    ExchangeRateDto save(SaveExchangeRateRequest saveRequest) throws AlreadyExistsException;

    ExchangeRateDto updateRateByCurrencyCodes(UpdateExchangeRateRequest updateRequest) throws NotFoundException;

}
