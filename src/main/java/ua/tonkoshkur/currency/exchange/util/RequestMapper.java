package ua.tonkoshkur.currency.exchange.util;

import jakarta.servlet.http.HttpServletRequest;

public interface RequestMapper<T> {

    T map(HttpServletRequest request);
}
