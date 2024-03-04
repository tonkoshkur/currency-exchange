package ua.tonkoshkur.currency.exchange.util;

import ua.tonkoshkur.currency.exchange.exception.BadRequestException;

public interface Validator<T> {

    void validate(T object) throws BadRequestException;

}
