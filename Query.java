public class Query {

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
