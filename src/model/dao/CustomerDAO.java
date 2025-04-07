package model.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import util.ServerLogger;
import db.DatabaseConnectionPool;

public class CustomerDAO {
    private CustomerDAO(){}

    public static List<String> getAllCustomers() {
        Connection connection = null;

        List<String> users = null;

        PreparedStatement preparedStatement = null;
        String query = "select * from customer";

        try {
            connection = DatabaseConnectionPool.getConnection();
            users = new ArrayList<>();

            // Sample query to be replaced with
            preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();
            ResultSetMetaData metaData = resultSet.getMetaData();

            // NOTE:
            // future optimization to be done is to replace the inner
            // for loop with the specific column names of the table
            // might introduce spaghetti code but the performance will be better

            while (resultSet.next()) {
                for (int i = 1; i <= metaData.getColumnCount(); i++) {
                    users.add(resultSet.getString(metaData.getColumnLabel(i)));
                }
                users.add("\n");
            }

            preparedStatement.close();
        } catch (SQLException e) {
            ServerLogger.severe(CustomerDAO.class.getName(), "getAllCustomers SQLException: " + e.getMessage());
        } finally {
            DatabaseConnectionPool.releaseConnection(connection);
        }

        return users;
    }

    // actions to be done on the customer table will go here
}
