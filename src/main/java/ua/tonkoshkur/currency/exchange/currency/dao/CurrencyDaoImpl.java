package ua.tonkoshkur.currency.exchange.currency.dao;

import org.sqlite.SQLiteErrorCode;
import org.sqlite.SQLiteException;
import ua.tonkoshkur.currency.exchange.currency.dao.entity.Currency;
import ua.tonkoshkur.currency.exchange.exception.AlreadyExistsException;
import ua.tonkoshkur.currency.exchange.exception.DatabaseException;
import ua.tonkoshkur.currency.exchange.util.ResultSetMapper;

import javax.sql.DataSource;
import java.sql.*;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class CurrencyDaoImpl implements CurrencyDao {

    private final DataSource dataSource;
    private final ResultSetMapper<Currency> resultSetMapper;

    public CurrencyDaoImpl(DataSource dataSource, ResultSetMapper<Currency> resultSetMapper) {
        this.dataSource = dataSource;
        this.resultSetMapper = resultSetMapper;
    }

    @Override
    public List<Currency> findAll() throws DatabaseException {
        String sql = "select * from Currencies";


        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement()) {

            ResultSet resultSet = statement.executeQuery(sql);
            return resultSetMapper.map(resultSet);

        } catch (SQLException e) {
            throw new DatabaseException(e);
        }
    }

    @Override
    public List<Currency> findByIds(Collection<Integer> ids) {
        List<String> placeholdersList = Collections.nCopies(ids.size(), "?");
        String placeholders = String.join(",", placeholdersList);
        String sql = "select * from Currencies where ID in (" + placeholders + ")";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            // Sqlite jdbc driver uses jdbc4 that doesn't implement createArrayOf method
            int index = 1;
            for (int id : ids) {
                statement.setInt(index++, id);
            }

            ResultSet resultSet = statement.executeQuery();

            return resultSetMapper.map(resultSet);

        } catch (SQLException e) {
            throw new DatabaseException(e);
        }
    }

    @Override
    public Optional<Currency> findById(int id) throws DatabaseException {
        String sql = "select * from Currencies where ID = ?";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, id);

            ResultSet resultSet = statement.executeQuery();

            return resultSetMapper.map(resultSet)
                    .stream()
                    .findAny();

        } catch (SQLException e) {
            throw new DatabaseException(e);
        }
    }

    @Override
    public Optional<Currency> findByCode(String code) throws DatabaseException {
        String sql = "select * from Currencies where Code=UPPER(?)";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, code);

            ResultSet resultSet = statement.executeQuery();

            return resultSetMapper.map(resultSet)
                    .stream()
                    .findAny();

        } catch (SQLException e) {
            throw new DatabaseException(e);
        }
    }

    @Override
    public void save(Currency currency) throws AlreadyExistsException, DatabaseException {
        String sql = "insert into Currencies (Code, FullName, Sign) values (UPPER(?), ?, ?)";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, currency.code());
            statement.setString(2, currency.name());
            statement.setString(3, currency.sign());

            statement.executeUpdate();

        } catch (SQLException e) {
            if (e instanceof SQLiteException sqLiteException
                    && sqLiteException.getResultCode().equals(SQLiteErrorCode.SQLITE_CONSTRAINT_UNIQUE)) {
                throw new AlreadyExistsException("Currency already exists with code " + currency.code());
            }
            throw new DatabaseException(e);
        }
    }

}
