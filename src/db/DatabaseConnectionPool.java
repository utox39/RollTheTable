package db;

import util.ServerLogger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

public class DatabaseConnectionPool {

    private static final List<Connection> connections;

    static {
        connections = new LinkedList<>();
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            ServerLogger.severe(DatabaseConnectionPool.class.getName(), "driver not found: " + e.getMessage());
        }
    }

    private DatabaseConnectionPool() {}

    private static synchronized Connection createDBConnection() throws SQLException {
        Connection newConnection = null;
        String ip = "localhost";
        String port = "3306";
        String db = "roll_the_table";
        String username = "root";
        String password = "password";

        newConnection = DriverManager.getConnection("jdbc:mysql://"+ ip+":"+ port+"/"+db+"?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", username, password);
        newConnection.setAutoCommit(false);
        return newConnection;
    }

    public static synchronized Connection getConnection() throws SQLException {
        Connection connection;

        if (!connections.isEmpty()) {
            connection = connections.get(0);
            connections.remove(0);

            try {
                if (connection.isClosed()) {
                    connection = getConnection();
                }
            } catch (SQLException e) {
                connection.close();
                connection = getConnection();
            }
        } else {
            connection = createDBConnection();
        }

        return connection;
    }

    public static synchronized void releaseConnection(Connection connection) {
        if (connection != null){
            connections.add(connection);
        }
    }
}
