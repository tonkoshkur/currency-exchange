package ua.tonkoshkur.currency.exchange.exchange.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ua.tonkoshkur.currency.exchange.exchange.dto.CalculateExchangeRequest;
import ua.tonkoshkur.currency.exchange.exchange.dto.ExchangeDto;
import ua.tonkoshkur.currency.exchange.exchange.service.ExchangeService;
import ua.tonkoshkur.currency.exchange.exchange.servlet.mapper.CalculateExchangeRequestMapper;
import ua.tonkoshkur.currency.exchange.util.BeanFactory;
import ua.tonkoshkur.currency.exchange.util.ResponseWriter;

import java.io.IOException;

@WebServlet("/exchange")
public class ExchangeServlet extends HttpServlet {

    private final ExchangeService exchangeService;
    private final CalculateExchangeRequestMapper calculateExchangeRequestMapper;

    public ExchangeServlet() {
        this.exchangeService = BeanFactory.getExchangeService();
        this.calculateExchangeRequestMapper = new CalculateExchangeRequestMapper();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        CalculateExchangeRequest request = calculateExchangeRequestMapper.map(req);

        ExchangeDto exchange = exchangeService.calculate(request.fromCurrencyCode(), request.toCurrencyCode(), request.amount());

        ResponseWriter.write(exchange, resp);

        resp.setStatus(HttpServletResponse.SC_OK);
    }
}
