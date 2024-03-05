package ua.tonkoshkur.currency.exchange.rate.servlet.mapper;

import jakarta.servlet.http.HttpServletRequest;
import ua.tonkoshkur.currency.exchange.exception.BadRequestException;
import ua.tonkoshkur.currency.exchange.rate.dto.SaveExchangeRateRequest;
import ua.tonkoshkur.currency.exchange.rate.servlet.extractor.RateValueExtractor;
import ua.tonkoshkur.currency.exchange.util.RequestMapper;

import java.io.IOException;
import java.math.BigDecimal;

public class SaveExchangeRateRequestMapper implements RequestMapper<SaveExchangeRateRequest> {

    @Override
    public SaveExchangeRateRequest map(HttpServletRequest request) throws BadRequestException, IOException {
        String baseCurrencyCode = request.getParameter("baseCurrencyCode");
        String targetCurrencyCode = request.getParameter("targetCurrencyCode");
        if (baseCurrencyCode == null) {
            throw new BadRequestException("Base currency code is required");
        }
        if (targetCurrencyCode == null) {
            throw new BadRequestException("Target currency code is required");
        }

        String rateParam = request.getParameter("rate");
        BigDecimal rate = RateValueExtractor.extract(rateParam);

        return new SaveExchangeRateRequest(baseCurrencyCode, targetCurrencyCode, rate);
    }
}
