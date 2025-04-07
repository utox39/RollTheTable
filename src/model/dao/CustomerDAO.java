package model;

import util.ConnectionUtil;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import util.ServerLogger;

public class CustomerDAO {

    private static final Connection con = ConnectionUtil.connect();

    private CustomerDAO(){}

//    private CustomerDAO() {
//        con = ConnectionUtil.connect();
//
//        if (con == null) {
//            throw new RuntimeException("Error occurred while connecting to the database");
//        }
//    }

    public static List<String> getAllCustomers() {
        List<String> users = null;

        try {
            users = new ArrayList<>();

            // Sample query to be replaced with
            PreparedStatement preparedStatement = con.prepareStatement("select * from customer;");
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

        } catch (SQLException e) {
            ServerLogger.severe(CustomerDAO.class.getName(), "getAllCustomers SQLException: " + e.getMessage());
        }

        return users;
    }

    // actions to be done on the customer table will go here
}
