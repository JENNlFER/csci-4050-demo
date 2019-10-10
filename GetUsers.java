import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class GetUsers extends CallableStatement<List<String>> {

    private final String status;

    public GetUsers(DataSource db, String status) {
        super(db, DemoData.SELECT_USER);
        this.status = status;
    }

    @Override
    protected List<String> query() throws SQLException {
        prepareStatement();
        statement.setString(1, status);
        result = statement.executeQuery();

        List<String> users = new ArrayList<>();

        while (result.next()) {
            users.add(result.getString(1));
        }

        return users;
    }
}
