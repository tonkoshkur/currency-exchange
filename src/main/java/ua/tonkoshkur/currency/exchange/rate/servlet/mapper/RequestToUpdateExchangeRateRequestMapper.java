package ua.tonkoshkur.currency.exchange.rate.servlet.mapper;

import jakarta.servlet.http.HttpServletRequest;
import ua.tonkoshkur.currency.exchange.exception.BadRequestException;
import ua.tonkoshkur.currency.exchange.rate.dto.UpdateExchangeRateRequest;
import ua.tonkoshkur.currency.exchange.rate.servlet.extractor.CurrencyCodesFromPathExtractor;
import ua.tonkoshkur.currency.exchange.rate.servlet.extractor.RateValueExtractor;
import ua.tonkoshkur.currency.exchange.util.RequestMapper;
import ua.tonkoshkur.currency.exchange.util.RequestReader;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Map;

public class RequestToUpdateExchangeRateRequestMapper implements RequestMapper<UpdateExchangeRateRequest> {

    @Override
    public UpdateExchangeRateRequest map(HttpServletRequest request) throws BadRequestException, IOException {
        String[] codes = CurrencyCodesFromPathExtractor.extract(request);
        String baseCurrencyCode = codes[0];
        String targetCurrencyCode = codes[1];

        Map<String, String> keyValueMap = RequestReader.read(request);
        String rateParam = keyValueMap.get("rate");
        BigDecimal rate = RateValueExtractor.extract(rateParam);

        return new UpdateExchangeRateRequest(baseCurrencyCode, targetCurrencyCode, rate);
    }
}
