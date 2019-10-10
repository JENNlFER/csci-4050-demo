public class Query {

    public static final String CREATE_TABLE_USERS =
            "CREATE TABLE IF NOT EXISTS users (" +
                    "id INT NOT NULL AUTO_INCREMENT," +
                    "first_name VARCHAR(255) NOT NULL," +
                    "last_name VARCHAR(255)," +
                    "email VARCHAR(255) NOT NULL," +
                    "status ENUM ('UNVERIFIED', 'VERIFIED', 'SUSPENDED') NOT NULL DEFAULT 'UNVERIFIED'," +
                    "created TIMESTAMP DEFAULT CURRENT_TIMESTAMP," +
                    "password VARCHAR(128) NOT NULL," +
                    "PRIMARY KEY (id), UNIQUE (email)" +
            ");";

    public static final String INSERT_USER =
            "INSERT INTO users (first_name, last_name, email, password) VALUES (?, ?, ?, ?);";

    public static final String SELECT_USER =
            "SELECT CONCAT(first_name, ' ', last_name) AS name, status FROM users WHERE status = ?;";

    public static final String[] SAMPLE_USERS = {
            "Monique Dill mdill@gmail.com 2gyri4utw",
            "Bernarda Determan Bernarda@hotmail.com pasWORD",
            "Leesa Yunker Leesa1998@gmail.com 1234567",
            "Delmer Schmuck schmuck@yahoo.com 278f3cg0vth",
            "Emilee Gridley egrindley@uga.edu securepassword",
            "Tyson Vanriper TVRiper@supermail.com sweetroll",
            "Ernie Enfinger ajdberl@gmail.com letmein",
            "Tabitha Juergens tjuergens@hotmail.com 101010101",
            "Chung Pehrson thisisan@email.com helloworld"
    };
}
