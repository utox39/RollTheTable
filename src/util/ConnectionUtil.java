package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionUtil {

    private ConnectionUtil() {}

    public static synchronized Connection connect() {

        try {
            Class.forName("com.mysql.cj.jdbc.Driver"); // must use
            String url = "jdbc:mysql://localhost:3306/roll_the_table";
            String user = "root";
            String password = "password";

            return DriverManager.getConnection(url, user, password);
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public static void close(Connection connection) {
        try {
            connection.close();
        }catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
