import com.mysql.cj.jdbc.MysqlDataSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class Main {

    public static void main(String[] args) throws SQLException {
        MysqlDataSource mysql = new MysqlDataSource();
        mysql.setServerName("localhost");
        mysql.setPort(3306);
        mysql.setDatabaseName("demo");
        mysql.setUser("root");
        mysql.setPassword("password");
        mysql.setServerTimezone("EST");


        createUser(mysql, "Jennifer", "Teissler", "jteissler@uga.edu", "password");
        updateUserStatus(mysql, "Jennifer", "VERIFIED");
        getUsers(mysql, "VERIFIED");
        generateUsers(mysql);
    }


    /**
     * A basic query to create a user with minimal error handling.
     */
    private static void createUser(MysqlDataSource db, String firstName, String lastName, String email, String password) {
        try {
            Connection connection = db.getConnection();
            PreparedStatement statement = connection.prepareStatement(DemoData.CREATE_USER);
            statement.setString(1, firstName);
            statement.setString(2, lastName);
            statement.setString(3, email);
            statement.setString(4, password);
            statement.executeUpdate();
            statement.close();
            connection.close();
        }
        catch (SQLException e) {
            System.err.println("User already created");
        }
    }

    /**
     * A basic query to update a user's status with minimal error handling.
     */
    private static void updateUserStatus(MysqlDataSource db, String firstName, String status) {
        try {
            Connection connection = db.getConnection();
            PreparedStatement statement = connection.prepareStatement(DemoData.UPDATE_USER_STATUS);
            statement.setString(1, status);
            statement.setString(2, firstName);
            statement.executeUpdate();
            statement.close();
            connection.close();
        }
        catch (SQLException e) {
            System.err.println("User status unable to be updated");
        }
    }

    /**
     * A query using {@link CallableStatement} to auto-handle connection initiation and cleanup.
     */
    private static void getUsers(MysqlDataSource db, String status) {
        try {
            new GetUsers(db, status).call().forEach(System.out::println);
        }
        catch (SQLException e) {
            System.err.println("Unable to select users.");
        }
    }

    /**
     * A query using {@link CallableStatement} to auto-handle connection initiation and cleanup
     * while running the query asynchronously as to not block the main thread.
     */
    private static void generateUsers(MysqlDataSource db) {
        ExecutorService executor = Executors.newFixedThreadPool(1);
        Callable<List<Integer>> statement = new GenerateUsers(db, DemoData.SAMPLE_USERS);
        Future<List<Integer>> future = executor.submit(statement);

        try {
            for (Integer id : future.get()) {
                System.out.println("Created user with ID: " + id + ".");
            }
        }
        catch (InterruptedException | ExecutionException e) {
            System.err.println("Users already generated");
        }

        executor.shutdown();
    }
}
