package ua.tonkoshkur.currency.exchange.rate.servlet.extractor;

import jakarta.servlet.http.HttpServletRequest;
import ua.tonkoshkur.currency.exchange.exception.BadRequestException;
import ua.tonkoshkur.currency.exchange.util.PathVariableExtractor;

public class CurrencyCodesFromPathExtractor {
    private CurrencyCodesFromPathExtractor() {
    }

    public static String[] extract(HttpServletRequest request) throws BadRequestException {
        String codes = PathVariableExtractor.extract(request.getPathInfo());

        if (codes.length() != 6) {
            throw new BadRequestException("Invalid currency codes");
        }

        String firstCode = codes.substring(0, 3);
        String secondCode = codes.substring(3, 6);

        return new String[]{firstCode, secondCode};
    }
}
