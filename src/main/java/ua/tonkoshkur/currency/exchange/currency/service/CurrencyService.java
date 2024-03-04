package ua.tonkoshkur.currency.exchange.currency.service;

import ua.tonkoshkur.currency.exchange.currency.dto.CurrencyDto;
import ua.tonkoshkur.currency.exchange.exception.AlreadyExistsException;
import ua.tonkoshkur.currency.exchange.exception.NotFoundException;

import java.util.List;

public interface CurrencyService {

    List<CurrencyDto> findAll();

    CurrencyDto findById(int id) throws NotFoundException;

    CurrencyDto findByCode(String code) throws NotFoundException;

    CurrencyDto save(CurrencyDto currency) throws AlreadyExistsException;

}
