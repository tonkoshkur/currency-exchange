package ua.tonkoshkur.currency.exchange.filter;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletResponse;
import ua.tonkoshkur.currency.exchange.exception.AlreadyExistsException;
import ua.tonkoshkur.currency.exchange.exception.BadRequestException;
import ua.tonkoshkur.currency.exchange.exception.NotFoundException;
import ua.tonkoshkur.currency.exchange.util.ResponseWriter;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import static jakarta.servlet.http.HttpServletResponse.*;

@WebFilter("/*")
public class ExceptionHandlingFilter implements Filter {

    private static final Logger LOGGER = Logger.getLogger(ExceptionHandlingFilter.class.getName());

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        try {
            chain.doFilter(request, response);
        } catch (Exception e) {
            HttpServletResponse httpResponse = (HttpServletResponse) response;

            int status = getStatusByException(e);
            httpResponse.setStatus(status);

            String errorMessage = e.getMessage();

            if (status == SC_INTERNAL_SERVER_ERROR) {
                LOGGER.log(Level.SEVERE, errorMessage, e);
            }

            ErrorResponse errorResponse = new ErrorResponse(errorMessage);
            ResponseWriter.write(errorResponse, httpResponse);
        }
    }

    private int getStatusByException(Exception e) {
        if (e instanceof BadRequestException) return SC_BAD_REQUEST;
        else if (e instanceof NotFoundException) return SC_NOT_FOUND;
        else if (e instanceof AlreadyExistsException) return SC_CONFLICT;
        else return SC_INTERNAL_SERVER_ERROR;
    }

    private record ErrorResponse(String message) {
    }

}
