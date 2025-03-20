package model;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CustomerDAO {

    private CustomerDAO() {
    }

    public static List<String> getAllCustomers() {
        List<String> users = null;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver"); // must use
            String url = "jdbc:mysql://localhost:3306/roll_the_table";
            String user = "root";
            String password = "password";

            Connection connection = DriverManager.getConnection(url, user, password);

            users = new ArrayList<>();

            // Sample query to be replaced with
            PreparedStatement preparedStatement = connection.prepareStatement("select * from customer;");
            ResultSet resultSet = preparedStatement.executeQuery();
            ResultSetMetaData metaData = resultSet.getMetaData();


            // NOTE:
            // future optimization to be done is to replace the inner
            // for loop with the specific column names of the table
            // might introduce spaghetti code but the performance will be better

            // O(n + m)
            // n = number of records; m number of columns

            while (resultSet.next()) {
                for (int i = 1; i <= metaData.getColumnCount(); i++) {
                    users.add(resultSet.getString(metaData.getColumnLabel(i)));
                }
                users.add("\n");
            }

        } catch (Exception e) {
            System.err.println(e.getMessage());
        }

        return users;
    }

    // actions to be done on the customer table will go here

}
