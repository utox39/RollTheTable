package model.dao;

import db.DatabaseConnectionPool;
import model.AdminModel;
import model.bean.AdminBean;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AdminDAO implements AdminModel {

    public synchronized AdminBean doRetriveById(int id) throws SQLException{
        Connection connection = null;
        AdminBean adminBean = null;

        PreparedStatement preparedStatement = null;
        String sql = "SELECT username, email, psw_hash FROM admin WHERE admin_id = ?";

        try {
            connection = DatabaseConnectionPool.getConnection();
            preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setInt(1, id);

            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                adminBean = new AdminBean();
                adminBean.setAdminId(id);
                adminBean.setUsername(resultSet.getString("username"));
                adminBean.setEmail(resultSet.getString("email"));
                adminBean.setPswHash(resultSet.getString("psw_hash"));
            }
        } finally {
            if (preparedStatement != null) {
                preparedStatement.close();
            }
            DatabaseConnectionPool.releaseConnection(connection);
        }

        return adminBean;
    }

    public synchronized AdminBean authenticate(String login, String pswHash) throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        String sql = "SELECT admin_id, psw_hash FROM admin WHERE (username = ? OR email = ?)";

        try {
            connection = DatabaseConnectionPool.getConnection();
            preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setString(1, login);
            preparedStatement.setString(2, login);

            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                int id = resultSet.getInt("admin_id");
                String adminPswHash = resultSet.getString("psw_hash");

                if (adminPswHash.equals(pswHash)) {
                    DatabaseConnectionPool.releaseConnection(connection);
                    return this.doRetriveById(id);
                }
            }
        } finally {
            if (preparedStatement != null) {
                preparedStatement.close();
            }
            DatabaseConnectionPool.releaseConnection(connection);
        }

        return null;
    }
}
