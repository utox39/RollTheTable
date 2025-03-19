package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class CustomerDAO {

    private CustomerDAO() {
    }

    public static List<String> test() {
        List<String> users = null;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver"); // must use
            String url = "jdbc:mysql://localhost:3306/roll_the_table";
            String user = "root";
            String password = "password";

            Connection connection = DriverManager.getConnection(url, user, password);

            users = new ArrayList<>();

            PreparedStatement preparedStatement = connection.prepareStatement("select * from customer;");
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                users.add(resultSet.getString("name"));
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }

        return users;
    }

    // actions to be done on the customer table will go here

}
