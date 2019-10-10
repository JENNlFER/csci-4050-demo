import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.concurrent.Callable;

/**
 * @author Jennifer Teissler
 */
public abstract class PreparedStatementCallable<T> implements Callable<T> {

    protected String query;
    protected DataSource db;

    protected Connection connection;
    protected PreparedStatement statement;
    protected ResultSet result;

    protected PreparedStatementCallable(DataSource db, String query) {
        this.query = query;
        this.db = db;
    }

    protected abstract T query() throws SQLException;

    public T call() throws SQLException {
        try {
            connection = db.getConnection();

            if (connection != null) {
                return query();
            } else {
                throw new SQLException("Could not connect to database");
            }
        } finally {
            if (connection != null) connection.close();
            if (statement != null) statement.close();
            if (result != null) result.close();
        }
    }

    protected void prepareStatement(boolean keygen) throws SQLException {
        if (statement != null && !statement.isClosed()) {
            throw new SQLException("Statement is already prepared");
        }

        statement = connection.prepareStatement(query,
                keygen ? Statement.RETURN_GENERATED_KEYS : Statement.NO_GENERATED_KEYS);
    }

    protected void prepareStatement() throws SQLException {
        prepareStatement(false);
    }
}

