package ua.tonkoshkur.currency.exchange.exchange.extractor;

import ua.tonkoshkur.currency.exchange.exception.BadRequestException;

import java.math.BigDecimal;

public class AmountValueExtractor {
    private AmountValueExtractor() {
    }

    public static BigDecimal extract(String amount) throws BadRequestException {
        if (amount == null) {
            throw new BadRequestException("Amount is required");
        }

        try {
            return new BigDecimal(amount);
        } catch (NullPointerException | NumberFormatException e) {
            throw new BadRequestException("Amount must be a number");
        }
    }
}
