package ua.tonkoshkur.currency.exchange.currency.servlet.mapper;

import jakarta.servlet.http.HttpServletRequest;
import ua.tonkoshkur.currency.exchange.currency.dto.CurrencyDto;
import ua.tonkoshkur.currency.exchange.exception.BadRequestException;
import ua.tonkoshkur.currency.exchange.util.RequestMapper;

import java.io.IOException;

public class RequestToCurrencyDtoMapper implements RequestMapper<CurrencyDto> {

    @Override
    public CurrencyDto map(HttpServletRequest request) throws BadRequestException, IOException {

        String idParam = request.getParameter("id");
        Integer id = Integer.getInteger(idParam);

        String name = request.getParameter("name");
        String code = request.getParameter("code");
        String sign = request.getParameter("sign");

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

        return new CurrencyDto(id, name, code, sign);
    }
}
