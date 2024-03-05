package ua.tonkoshkur.currency.exchange.rate.dao.mapper;

import ua.tonkoshkur.currency.exchange.rate.dao.entity.ExchangeRate;
import ua.tonkoshkur.currency.exchange.util.ResultSetMapper;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ResultSetToExchangeRateMapper extends ResultSetMapper<ExchangeRate> {

    @Override
    protected ExchangeRate mapRow(ResultSet resultSet) throws SQLException {
        int id = resultSet.getInt("ID");
        int baseCurrencyId = resultSet.getInt("BaseCurrencyId");
        int targetCurrencyId = resultSet.getInt("TargetCurrencyId");
        BigDecimal rate = resultSet.getBigDecimal("Rate");

        return new ExchangeRate(id, baseCurrencyId, targetCurrencyId, rate);
    }

}
