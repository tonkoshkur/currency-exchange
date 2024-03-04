package ua.tonkoshkur.currency.exchange.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ResponseWriter {

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
    private static final Logger LOGGER = Logger.getLogger(ResponseWriter.class.getName());

    private ResponseWriter() {
    }

    public static void write(Object object, HttpServletResponse response) {
        try {
            OBJECT_MAPPER.writeValue(response.getWriter(), object);
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, e.getMessage(), e);
        }
    }
}
