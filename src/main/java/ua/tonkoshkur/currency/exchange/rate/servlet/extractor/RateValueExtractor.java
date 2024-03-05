package ua.tonkoshkur.currency.exchange.rate.servlet.extractor;

import ua.tonkoshkur.currency.exchange.exception.BadRequestException;

import java.math.BigDecimal;

public class RateValueExtractor {
    private RateValueExtractor() {
    }

    public static BigDecimal extract(String rate) throws BadRequestException {
        if (rate == null) {
            throw new BadRequestException("Rate is required");
        }

        try {
            return new BigDecimal(rate);
        } catch (NullPointerException | NumberFormatException e) {
            throw new BadRequestException("Rate must be a number");
        }
    }
}
