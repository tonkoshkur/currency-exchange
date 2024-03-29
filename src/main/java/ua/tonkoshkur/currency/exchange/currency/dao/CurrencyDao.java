package ua.tonkoshkur.currency.exchange.currency.dao;

import ua.tonkoshkur.currency.exchange.currency.dao.entity.Currency;
import ua.tonkoshkur.currency.exchange.exception.AlreadyExistsException;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface CurrencyDao {

    List<Currency> findAll();

    List<Currency> findByIds(Collection<Integer> ids);

    Optional<Currency> findById(int id);

    Optional<Currency> findByCode(String code);

    void save(Currency currency) throws AlreadyExistsException;

}
