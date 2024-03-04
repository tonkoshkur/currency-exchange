package ua.tonkoshkur.currency.exchange.util;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public abstract class ResultSetMapper<T> {

    public List<T> map(ResultSet resultSet) throws SQLException {

        List<T> list = new ArrayList<>();

        while (resultSet.next()) {
            T entity = mapRow(resultSet);
            list.add(entity);
        }

        return list;
    }

    protected abstract T mapRow(ResultSet resultSet) throws SQLException;

}
