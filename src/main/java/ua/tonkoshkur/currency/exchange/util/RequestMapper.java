package ua.tonkoshkur.currency.exchange.util;

import jakarta.servlet.http.HttpServletRequest;
import ua.tonkoshkur.currency.exchange.exception.BadRequestException;

import java.io.IOException;

public interface RequestMapper<T> {

    T map(HttpServletRequest request) throws BadRequestException, IOException;
}
