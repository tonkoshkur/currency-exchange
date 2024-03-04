package ua.tonkoshkur.currency.exchange.currency.mapper;

import ua.tonkoshkur.currency.exchange.currency.dao.entity.Currency;
import ua.tonkoshkur.currency.exchange.currency.dto.CurrencyDto;
import ua.tonkoshkur.currency.exchange.util.ObjectMapper;

public class CurrencyMapper implements ObjectMapper<CurrencyDto, Currency> {

    public CurrencyDto toDto(Currency entity) {
        return new CurrencyDto(entity.id(), entity.name(), entity.code(), entity.sign());
    }

    public Currency toEntity(CurrencyDto dto) {
        return new Currency(dto.id(), dto.name(), dto.code(), dto.sign());
    }
}
