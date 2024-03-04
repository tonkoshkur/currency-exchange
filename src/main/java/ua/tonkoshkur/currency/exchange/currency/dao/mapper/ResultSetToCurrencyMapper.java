package ua.tonkoshkur.currency.exchange.currency.dao.mapper;

import ua.tonkoshkur.currency.exchange.currency.dao.entity.Currency;
import ua.tonkoshkur.currency.exchange.util.ResultSetMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ResultSetToCurrencyMapper extends ResultSetMapper<Currency> {

    @Override
    protected Currency mapRow(ResultSet resultSet) throws SQLException {
        int id = resultSet.getInt("ID");
        String name = resultSet.getString("FullName");
        String code = resultSet.getString("Code");
        String sign = resultSet.getString("Sign");

        return new Currency(id, name, code, sign);
    }

}
