package ua.tonkoshkur.currency.exchange.currency.service;

import ua.tonkoshkur.currency.exchange.currency.dao.CurrencyDao;
import ua.tonkoshkur.currency.exchange.currency.dao.entity.Currency;
import ua.tonkoshkur.currency.exchange.currency.dto.CurrencyDto;
import ua.tonkoshkur.currency.exchange.exception.AlreadyExistsException;
import ua.tonkoshkur.currency.exchange.exception.NotFoundException;
import ua.tonkoshkur.currency.exchange.exception.UnknownException;
import ua.tonkoshkur.currency.exchange.util.ObjectMapper;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class CurrencyServiceImpl implements CurrencyService {

    private final CurrencyDao currencyDao;
    private final ObjectMapper<CurrencyDto, Currency> mapper;

    public CurrencyServiceImpl(CurrencyDao currencyDao, ObjectMapper<CurrencyDto, Currency> currencyMapper) {
        this.currencyDao = currencyDao;
        this.mapper = currencyMapper;
    }

    @Override
    public List<CurrencyDto> findAll() {
        List<Currency> currencies = currencyDao.findAll();
        return mapper.toDto(currencies);
    }

    @Override
    public Map<Integer, CurrencyDto> findMapByIds(Collection<Integer> ids) {
        return currencyDao.findByIds(ids)
                .stream()
                .map(mapper::toDto)
                .collect(Collectors.toMap(CurrencyDto::id, Function.identity()));
    }

    @Override
    public CurrencyDto findById(int id) throws NotFoundException {
        return currencyDao.findById(id)
                .map(mapper::toDto)
                .orElseThrow(() -> new NotFoundException("Currency not found by id " + id));
    }

    @Override
    public CurrencyDto findByCode(String code) throws NotFoundException {
        return currencyDao.findByCode(code)
                .map(mapper::toDto)
                .orElseThrow(() -> new NotFoundException("Currency not found with code " + code));
    }

    @Override
    public CurrencyDto save(CurrencyDto currency) throws AlreadyExistsException {
        Currency entity = mapper.toEntity(currency);

        currencyDao.save(entity);

        return currencyDao.findByCode(currency.code())
                .map(mapper::toDto)
                .orElseThrow(() -> new UnknownException("Currency not found after save"));
    }

}
