package ua.tonkoshkur.currency.exchange.rate.dao;

import ua.tonkoshkur.currency.exchange.exception.AlreadyExistsException;
import ua.tonkoshkur.currency.exchange.rate.dao.entity.ExchangeRate;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface ExchangeRateDao {

    List<ExchangeRate> findAll();

    Optional<ExchangeRate> findByBaseAndTargetCurrencyIds(int baseCurrencyId, int targetCurrencyId);

    void updateRateById(int id, BigDecimal rate);

    void save(ExchangeRate exchangeRate) throws AlreadyExistsException;

}
