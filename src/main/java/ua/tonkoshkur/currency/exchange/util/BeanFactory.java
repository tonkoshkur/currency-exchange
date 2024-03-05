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

import javax.sql.DataSource;

public class BeanFactory {

    private static final DataSource DATA_SOURCE;

    private static final CurrencyService CURRENCY_SERVICE;
    private static final CurrencyDao CURRENCY_DAO;

    static {
        DATA_SOURCE = createDataSource();

        CURRENCY_DAO = createCurrencyDao();
        CURRENCY_SERVICE = createCurrencyService();
    }

    private BeanFactory() {
    }

    public static CurrencyService getCurrencyService() {
        return CURRENCY_SERVICE;
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
}
