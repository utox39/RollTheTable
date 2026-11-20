package db;

import util.ServerLogger;

import java.sql.Connection;
import java.sql.SQLException;

import org.apache.tomcat.jdbc.pool.DataSource;
import org.apache.tomcat.jdbc.pool.PoolProperties;

public class DatabaseConnectionPool {

    private static final DataSource dataSource;

    static {
        PoolProperties p = new PoolProperties();
        String host = getEnvOrDefault("DB_HOST", "localhost");
        String port = getEnvOrDefault("DB_PORT", "3306");
        p.setUrl("jdbc:mysql://" + host + ":" + port + "/roll_the_table?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC");
        p.setDriverClassName("com.mysql.cj.jdbc.Driver");
        p.setUsername(getEnvOrDefault("DB_USER", "root"));
        p.setPassword(getEnvOrDefault("DB_PASSWORD", "password"));

        p.setJmxEnabled(true);
        p.setTestWhileIdle(true);
        p.setTestOnBorrow(true);
        p.setValidationQuery("SELECT 1");
        p.setTestOnReturn(false);
        p.setValidationInterval(30000);
        p.setTimeBetweenEvictionRunsMillis(30000);
        p.setMaxActive(100);
        p.setInitialSize(10);
        p.setMaxWait(10000);
        p.setRemoveAbandonedTimeout(60);
        p.setMinEvictableIdleTimeMillis(30000);
        p.setMinIdle(10);
        p.setLogAbandoned(true);
        p.setRemoveAbandoned(true);
        p.setDefaultAutoCommit(false);

        dataSource = new DataSource();
        dataSource.setPoolProperties(p);
    }

    DatabaseConnectionPool() {}

    private static String getEnvOrDefault(String name, String defaultValue) {
        String value = System.getenv(name);
        return (value == null || value.isEmpty()) ? defaultValue : value;
    }

    public static Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }

    public static void releaseConnection(Connection connection) {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                ServerLogger.severe(DatabaseConnectionPool.class.getName(), "releaseConnection failed to release the connection: " + e.getMessage());
            }
        }
    }
}
