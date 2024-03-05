package ua.tonkoshkur.currency.exchange.filter;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;

import java.io.IOException;

@WebFilter(urlPatterns = {"/currency/*", "/currencies", "/exchangeRate/*", "/exchangeRates", "/exchange"})
public class EncodingFilter implements Filter {

    private static final String UTF_8 = "UTF-8";

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        request.setCharacterEncoding(UTF_8);
        response.setCharacterEncoding(UTF_8);

        chain.doFilter(request, response);
    }

}