package model.dao;

import db.DatabaseConnectionPool;
import model.bean.AdminBean;
import util.ServerLogger;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AdminDAO {


    public boolean exists(AdminBean admin) throws SQLException {
        Connection connection = null;


        try {
            connection = DatabaseConnectionPool.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("select psw_hash from 'admin' where username = ?");
            preparedStatement.setString(1, admin.getUsername());

            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
               if (resultSet.getString("psw_hash").equals(admin.getPswHash())) {
                   return true;
               }
            }
        } catch (SQLException e) {
            ServerLogger.severe(AdminDAO.class.getName(), "exists: SQLException: " + e.getMessage());
        }

        return false;
    }
}
