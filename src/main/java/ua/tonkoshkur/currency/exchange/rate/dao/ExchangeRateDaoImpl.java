package ua.tonkoshkur.currency.exchange.rate.dao;

import org.sqlite.SQLiteErrorCode;
import org.sqlite.SQLiteException;
import ua.tonkoshkur.currency.exchange.exception.AlreadyExistsException;
import ua.tonkoshkur.currency.exchange.exception.DatabaseException;
import ua.tonkoshkur.currency.exchange.rate.dao.entity.ExchangeRate;
import ua.tonkoshkur.currency.exchange.util.ResultSetMapper;

import javax.sql.DataSource;
import java.math.BigDecimal;
import java.sql.*;
import java.util.List;
import java.util.Optional;

public class ExchangeRateDaoImpl implements ExchangeRateDao {

    private final DataSource dataSource;
    private final ResultSetMapper<ExchangeRate> resultSetMapper;

    public ExchangeRateDaoImpl(DataSource dataSource, ResultSetMapper<ExchangeRate> resultSetMapper) {
        this.dataSource = dataSource;
        this.resultSetMapper = resultSetMapper;
    }

    @Override
    public List<ExchangeRate> findAll() {
        String sql = "select * from ExchangeRates";

        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement()) {

            ResultSet resultSet = statement.executeQuery(sql);

            return resultSetMapper.map(resultSet);

        } catch (SQLException e) {
            throw new DatabaseException(e);
        }
    }

    @Override
    public Optional<ExchangeRate> findByBaseAndTargetCurrencyIds(int baseCurrencyId, int targetCurrencyId) {
        String sql = "select * from ExchangeRates where BaseCurrencyId = ? and TargetCurrencyId = ?";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, baseCurrencyId);
            statement.setInt(2, targetCurrencyId);

            ResultSet resultSet = statement.executeQuery();

            return resultSetMapper.map(resultSet)
                    .stream()
                    .findAny();

        } catch (SQLException e) {
            throw new DatabaseException(e);
        }
    }

    @Override
    public void save(ExchangeRate exchangeRate) {
        String sql = "insert into ExchangeRates (BaseCurrencyId, TargetCurrencyId, Rate) values (?,?,?)";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, exchangeRate.baseCurrencyId());
            statement.setInt(2, exchangeRate.targetCurrencyId());
            statement.setBigDecimal(3, exchangeRate.rate());

            statement.executeUpdate();

        } catch (SQLException e) {
            if (e instanceof SQLiteException sqLiteException
                    && sqLiteException.getResultCode().equals(SQLiteErrorCode.SQLITE_CONSTRAINT_UNIQUE)) {
                throw new AlreadyExistsException("Exchange rate already exists");
            }
            throw new DatabaseException(e);
        }
    }

    @Override
    public void updateRateById(int id, BigDecimal rate) {
        String sql = "update ExchangeRates set Rate = ? where ID = ?";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setBigDecimal(1, rate);
            statement.setInt(2, id);

            statement.executeUpdate();

        } catch (SQLException e) {
            throw new DatabaseException(e);
        }
    }
}
