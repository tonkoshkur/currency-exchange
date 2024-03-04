package ua.tonkoshkur.currency.exchange.util;

import ua.tonkoshkur.currency.exchange.exception.BadRequestException;

public final class PathVariableExecutor {
    private PathVariableExecutor() {
    }

    public static String execute(String path) throws BadRequestException {

        if (path != null) {
            String variable = path.substring(1);

            if (!variable.isBlank()) {
                return variable;
            }
        }

        throw new BadRequestException("Path variable is missing");
    }
}
