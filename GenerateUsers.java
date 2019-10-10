import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class GenerateUsers extends CallableStatement<List<Integer>> {

    private final String[] users;

    public GenerateUsers(DataSource db, String[] users) {
        super(db, DemoData.CREATE_USER);

        this.users = users;
    }

    @Override
    protected List<Integer> query() throws SQLException {
        prepareStatement(true);

        for (String user : users) {
            String[] data = user.split(" ");
            statement.setString(1, data[0]);
            statement.setString(2, data[1]);
            statement.setString(3, data[2]);
            statement.setString(4, data[3]);
            statement.addBatch();
        }

        statement.executeBatch();
        result = statement.getGeneratedKeys();
        List<Integer> userIds = new ArrayList<>();

        while (result.next()) {
            userIds.add(result.getInt(1));
        }

        return userIds;
    }
}
