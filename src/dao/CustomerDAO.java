package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class CustomerDAO {

    public static String test() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver"); // must use
            String url = "jdbc:mysql://localhost:3306/roll_the_table";
            String user = "root";
            String password = "password";

            Connection connection = DriverManager.getConnection(url, user, password);

            PreparedStatement preparedStatement = connection.prepareStatement("select * from customer;");
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                return resultSet.getString("name");
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }

        return null;
    }
}
