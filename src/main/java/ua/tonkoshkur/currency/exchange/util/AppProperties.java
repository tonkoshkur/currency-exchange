package ua.tonkoshkur.currency.exchange.util;

import ua.tonkoshkur.currency.exchange.exception.UnknownException;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class AppProperties {

    private static final Properties props;

    static {
        String rootPath = Thread.currentThread().getContextClassLoader().getResource("").getPath();
        String appConfigPath = rootPath + "app.properties";

        props = new Properties();
        try {
            props.load(new FileInputStream(appConfigPath));
        } catch (IOException e) {
            throw new UnknownException("Unable to load app properties", e);
        }
    }

    private AppProperties() {
    }

    public static String getDefaultCurrencyCode() {
        return props.getProperty("defaultCurrencyCode");
    }

    public static int getConvertedAmountDecimalPlaces() {
        return Integer.parseInt(props.getProperty("convertedAmount.decimalPlaces"));
    }

    public static String getDatabaseDriverClassName() {
        return props.getProperty("database.driver.className");
    }

    public static String getDatabaseUrl() {
        return props.getProperty("database.url");
    }

    public static int getConnectionPoolMinIdle() {
        return Integer.parseInt(props.getProperty("database.connection.pool.idle.min"));
    }

    public static int getConnectionPoolMaxIdle() {
        return Integer.parseInt(props.getProperty("database.connection.pool.idle.max"));
    }

    public static int getConnectionPoolMaxSize() {
        return Integer.parseInt(props.getProperty("database.connection.pool.size.max"));
    }
}
