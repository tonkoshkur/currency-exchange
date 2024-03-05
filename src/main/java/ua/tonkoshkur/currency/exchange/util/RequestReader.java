package ua.tonkoshkur.currency.exchange.util;

import jakarta.servlet.http.HttpServletRequest;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class RequestReader {
    private RequestReader() {
    }

    public static Map<String, String> read(HttpServletRequest request) throws IOException {
        try (BufferedReader reader = request.getReader()) {
            String data = reader.readLine();

            return Stream.of(data.split("&"))
                    .map(param -> param.split("="))
                    .collect(Collectors.toMap(keyValuePair -> keyValuePair[0], keyValuePair -> keyValuePair[1]));
        }
    }
}
