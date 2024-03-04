package ua.tonkoshkur.currency.exchange.currency.servlet.mapper;

import jakarta.servlet.http.HttpServletRequest;
import ua.tonkoshkur.currency.exchange.currency.dto.CurrencyDto;
import ua.tonkoshkur.currency.exchange.util.RequestMapper;

public class RequestToCurrencyDtoMapper implements RequestMapper<CurrencyDto> {

    @Override
    public CurrencyDto map(HttpServletRequest request) {

        String idParam = request.getParameter("id");
        Integer id = Integer.getInteger(idParam);

        String name = request.getParameter("name");
        String code = request.getParameter("code");
        String sign = request.getParameter("sign");

        return new CurrencyDto(id, name, code, sign);
    }
}
