package ua.tonkoshkur.currency.exchange.currency.servlet.validator;

import ua.tonkoshkur.currency.exchange.currency.dto.CurrencyDto;
import ua.tonkoshkur.currency.exchange.exception.BadRequestException;
import ua.tonkoshkur.currency.exchange.util.Validator;

public class CurrencyValidator implements Validator<CurrencyDto> {

    @Override
    public void validate(CurrencyDto currency) throws BadRequestException {
        String name = currency.name();
        String code = currency.code();
        String sign = currency.sign();

        if (name == null) {
            throw new BadRequestException("Name is required");
        }
        if (name.isBlank()) {
            throw new BadRequestException("Name mustn't be blank");
        }
        if (code == null) {
            throw new BadRequestException("Code is required");
        }
        if (code.isBlank()) {
            throw new BadRequestException("Code mustn't be");
        }
        if (sign == null) {
            throw new BadRequestException("Sign is required");
        }
        if (sign.isBlank()) {
            throw new BadRequestException("Sing mustn't be blank");
        }
    }
}
