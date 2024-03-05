package ua.tonkoshkur.currency.exchange.rate.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ua.tonkoshkur.currency.exchange.rate.dto.ExchangeRateDto;
import ua.tonkoshkur.currency.exchange.rate.dto.SaveExchangeRateRequest;
import ua.tonkoshkur.currency.exchange.rate.service.ExchangeRateService;
import ua.tonkoshkur.currency.exchange.rate.servlet.mapper.RequestToSaveExchangeRateRequestMapper;
import ua.tonkoshkur.currency.exchange.util.BeanFactory;
import ua.tonkoshkur.currency.exchange.util.ResponseWriter;

import java.io.IOException;
import java.util.List;

@WebServlet("/exchangeRates")
public class ExchangeRatesServlet extends HttpServlet {

    private final ExchangeRateService exchangeRateService;
    private final RequestToSaveExchangeRateRequestMapper requestToSaveRequestMapper;

    public ExchangeRatesServlet() {
        this.exchangeRateService = BeanFactory.getExchangeRateService();
        this.requestToSaveRequestMapper = new RequestToSaveExchangeRateRequestMapper();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<ExchangeRateDto> exchangeRates = exchangeRateService.findAll();

        ResponseWriter.write(exchangeRates, resp);

        resp.setStatus(HttpServletResponse.SC_OK);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        SaveExchangeRateRequest saveRequest = requestToSaveRequestMapper.map(req);

        ExchangeRateDto savedExchangeRate = exchangeRateService.save(saveRequest);

        ResponseWriter.write(savedExchangeRate, resp);

        resp.setStatus(HttpServletResponse.SC_CREATED);
    }
}
