package ua.tonkoshkur.currency.exchange.currency.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ua.tonkoshkur.currency.exchange.currency.dto.CurrencyDto;
import ua.tonkoshkur.currency.exchange.currency.service.CurrencyService;
import ua.tonkoshkur.currency.exchange.util.BeanFactory;
import ua.tonkoshkur.currency.exchange.util.PathVariableExtractor;
import ua.tonkoshkur.currency.exchange.util.ResponseWriter;

import java.io.IOException;

@WebServlet("/currency/*")
public class CurrencyServlet extends HttpServlet {

    private final CurrencyService currencyService;

    public CurrencyServlet() {
        currencyService = BeanFactory.getCurrencyService();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String code = PathVariableExtractor.extract(req.getPathInfo());

        CurrencyDto currency = currencyService.findByCode(code);

        ResponseWriter.write(currency, resp);

        resp.setStatus(HttpServletResponse.SC_OK);
    }
}
