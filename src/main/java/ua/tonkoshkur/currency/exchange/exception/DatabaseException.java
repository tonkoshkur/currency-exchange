package ua.tonkoshkur.currency.exchange.exception;

public class DatabaseException extends RuntimeException {
    public DatabaseException(String message, Throwable cause) {
        super(message, cause);
    }

    public DatabaseException(Throwable cause) {
        this("Database exception", cause);
    }
}
