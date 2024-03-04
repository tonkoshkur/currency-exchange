package ua.tonkoshkur.currency.exchange.currency.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ua.tonkoshkur.currency.exchange.currency.dto.CurrencyDto;
import ua.tonkoshkur.currency.exchange.currency.service.CurrencyService;
import ua.tonkoshkur.currency.exchange.currency.servlet.mapper.RequestToCurrencyDtoMapper;
import ua.tonkoshkur.currency.exchange.currency.servlet.validator.CurrencyValidator;
import ua.tonkoshkur.currency.exchange.util.BeanFactory;
import ua.tonkoshkur.currency.exchange.util.ResponseWriter;

import java.io.IOException;
import java.util.List;

@WebServlet("/currencies")
public class CurrenciesServlet extends HttpServlet {

    private final CurrencyService currencyService;
    private final RequestToCurrencyDtoMapper requestToCurrencyDtoMapper;
    private final CurrencyValidator currencyValidator;

    public CurrenciesServlet() {
        currencyService = BeanFactory.getCurrencyService();
        requestToCurrencyDtoMapper = new RequestToCurrencyDtoMapper();
        currencyValidator = new CurrencyValidator();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<CurrencyDto> currencyList = currencyService.findAll();

        ResponseWriter.write(currencyList, resp);

        resp.setStatus(HttpServletResponse.SC_OK);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        CurrencyDto currency = requestToCurrencyDtoMapper.map(req);

        currencyValidator.validate(currency);

        CurrencyDto savedCurrency = currencyService.save(currency);

        ResponseWriter.write(savedCurrency, resp);

        resp.setStatus(HttpServletResponse.SC_CREATED);
    }
}
