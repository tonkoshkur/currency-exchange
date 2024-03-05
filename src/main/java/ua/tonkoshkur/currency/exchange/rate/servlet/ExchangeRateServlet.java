package ua.tonkoshkur.currency.exchange.rate.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ua.tonkoshkur.currency.exchange.rate.dto.ExchangeRateDto;
import ua.tonkoshkur.currency.exchange.rate.dto.UpdateExchangeRateRequest;
import ua.tonkoshkur.currency.exchange.rate.service.ExchangeRateService;
import ua.tonkoshkur.currency.exchange.rate.servlet.extractor.CurrencyCodesFromPathExtractor;
import ua.tonkoshkur.currency.exchange.rate.servlet.mapper.UpdateExchangeRateRequestMapper;
import ua.tonkoshkur.currency.exchange.util.BeanFactory;
import ua.tonkoshkur.currency.exchange.util.ResponseWriter;
import ua.tonkoshkur.currency.exchange.util.WithPatchServlet;

import java.io.IOException;

@WebServlet("/exchangeRate/*")
public class ExchangeRateServlet extends WithPatchServlet {

    private final ExchangeRateService exchangeRateService;
    private final UpdateExchangeRateRequestMapper updateRequestMapper;

    public ExchangeRateServlet() {
        this.exchangeRateService = BeanFactory.getExchangeRateService();
        this.updateRequestMapper = new UpdateExchangeRateRequestMapper();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String[] codes = CurrencyCodesFromPathExtractor.extract(req);

        ExchangeRateDto exchangeRate = exchangeRateService.findByCurrencyCodes(codes[0], codes[1]);

        ResponseWriter.write(exchangeRate, resp);

        resp.setStatus(HttpServletResponse.SC_OK);
    }

    @Override
    protected void doPatch(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        UpdateExchangeRateRequest updateRequest = updateRequestMapper.map(req);

        ExchangeRateDto exchangeRate = exchangeRateService.updateRateByCurrencyCodes(updateRequest);

        ResponseWriter.write(exchangeRate, resp);

        resp.setStatus(HttpServletResponse.SC_OK);
    }
}
