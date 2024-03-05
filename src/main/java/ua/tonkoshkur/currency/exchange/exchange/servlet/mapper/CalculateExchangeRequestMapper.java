package ua.tonkoshkur.currency.exchange.exchange.servlet.mapper;

import jakarta.servlet.http.HttpServletRequest;
import ua.tonkoshkur.currency.exchange.exception.BadRequestException;
import ua.tonkoshkur.currency.exchange.exchange.dto.CalculateExchangeRequest;
import ua.tonkoshkur.currency.exchange.exchange.extractor.AmountValueExtractor;
import ua.tonkoshkur.currency.exchange.util.RequestMapper;

import java.io.IOException;
import java.math.BigDecimal;

public class CalculateExchangeRequestMapper implements RequestMapper<CalculateExchangeRequest> {

    @Override
    public CalculateExchangeRequest map(HttpServletRequest request) throws BadRequestException, IOException {
        return new CalculateExchangeRequest(
                getFromParamValue(request),
                getToParamValue(request),
                getAmount(request));
    }

    private String getFromParamValue(HttpServletRequest request) throws BadRequestException {
        String fromCurrencyCode = request.getParameter("from");
        if (fromCurrencyCode == null) {
            throw new BadRequestException("From currency code is required");
        }
        return fromCurrencyCode;
    }

    private String getToParamValue(HttpServletRequest request) throws BadRequestException {
        String toCurrencyCode = request.getParameter("to");
        if (toCurrencyCode == null) {
            throw new BadRequestException("To currency code is required");
        }
        return toCurrencyCode;
    }

    private BigDecimal getAmount(HttpServletRequest request) throws BadRequestException {
        String amount = request.getParameter("amount");
        return AmountValueExtractor.extract(amount);
    }
}
