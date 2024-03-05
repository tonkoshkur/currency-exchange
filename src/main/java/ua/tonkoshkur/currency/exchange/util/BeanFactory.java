package ua.tonkoshkur.currency.exchange.util;

import org.apache.commons.dbcp2.BasicDataSource;
import ua.tonkoshkur.currency.exchange.currency.dao.CurrencyDao;
import ua.tonkoshkur.currency.exchange.currency.dao.CurrencyDaoImpl;
import ua.tonkoshkur.currency.exchange.currency.dao.entity.Currency;
import ua.tonkoshkur.currency.exchange.currency.dao.mapper.ResultSetToCurrencyMapper;
import ua.tonkoshkur.currency.exchange.currency.dto.CurrencyDto;
import ua.tonkoshkur.currency.exchange.currency.mapper.CurrencyMapper;
import ua.tonkoshkur.currency.exchange.currency.service.CurrencyService;
import ua.tonkoshkur.currency.exchange.currency.service.CurrencyServiceImpl;
import ua.tonkoshkur.currency.exchange.exchange.service.ExchangeService;
import ua.tonkoshkur.currency.exchange.exchange.service.ExchangeServiceImpl;
import ua.tonkoshkur.currency.exchange.rate.dao.ExchangeRateDao;
import ua.tonkoshkur.currency.exchange.rate.dao.ExchangeRateDaoImpl;
import ua.tonkoshkur.currency.exchange.rate.dao.entity.ExchangeRate;
import ua.tonkoshkur.currency.exchange.rate.dao.mapper.ResultSetToExchangeRateMapper;
import ua.tonkoshkur.currency.exchange.rate.service.ExchangeRateService;
import ua.tonkoshkur.currency.exchange.rate.service.ExchangeRateServiceImpl;

import javax.sql.DataSource;

public class BeanFactory {

    private static final DataSource DATA_SOURCE;

    private static final CurrencyService CURRENCY_SERVICE;
    private static final CurrencyDao CURRENCY_DAO;

    private static final ExchangeRateService EXCHANGE_RATE_SERVICE;
    private static final ExchangeRateDao EXCHANGE_RATE_DAO;

    private static final ExchangeService EXCHANGE_SERVICE;

    static {
        DATA_SOURCE = createDataSource();

        CURRENCY_DAO = createCurrencyDao();
        CURRENCY_SERVICE = createCurrencyService();

        EXCHANGE_RATE_DAO = createExchangeRateDao();
        EXCHANGE_RATE_SERVICE = createExchangeRateService();

        EXCHANGE_SERVICE = createExchangeService();
    }

    private BeanFactory() {
    }

    public static CurrencyService getCurrencyService() {
        return CURRENCY_SERVICE;
    }

    public static ExchangeRateService getExchangeRateService() {
        return EXCHANGE_RATE_SERVICE;
    }

    public static ExchangeService getExchangeService() {
        return EXCHANGE_SERVICE;
    }

    private static DataSource createDataSource() {
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setDriverClassName("org.sqlite.JDBC");
        dataSource.setUrl("jdbc:sqlite::resource:currency_exchange.db");

        dataSource.setMinIdle(5);
        dataSource.setMaxIdle(10);
        dataSource.setMaxTotal(25);

        return dataSource;
    }

    private static CurrencyDao createCurrencyDao() {
        ResultSetToCurrencyMapper resultSetMapper = new ResultSetToCurrencyMapper();
        return new CurrencyDaoImpl(DATA_SOURCE, resultSetMapper);
    }

    private static CurrencyService createCurrencyService() {
        ObjectMapper<CurrencyDto, Currency> currencyMapper = new CurrencyMapper();
        return new CurrencyServiceImpl(CURRENCY_DAO, currencyMapper);
    }

    private static ExchangeRateDao createExchangeRateDao() {
        ResultSetMapper<ExchangeRate> resultSetMapper = new ResultSetToExchangeRateMapper();
        return new ExchangeRateDaoImpl(DATA_SOURCE, resultSetMapper);
    }

    private static ExchangeRateService createExchangeRateService() {
        return new ExchangeRateServiceImpl(EXCHANGE_RATE_DAO, CURRENCY_SERVICE);
    }

    private static ExchangeService createExchangeService() {
        return new ExchangeServiceImpl(CURRENCY_SERVICE, EXCHANGE_RATE_SERVICE, "USD", 4);
    }

}
