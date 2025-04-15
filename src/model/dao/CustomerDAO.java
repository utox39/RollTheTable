package model.dao;

import java.sql.*;

import db.DatabaseConnectionPool;
import model.CustomerModel;
import model.bean.CustomerBean;

public class CustomerDAO implements CustomerModel {

    public synchronized CustomerBean doRetriveById(int id) throws SQLException{
        Connection connection = null;
        CustomerBean customerBean = null;

        PreparedStatement preparedStatement = null;
        String sql = "SELECT `name`, surname, username, email, phone, psw_hash FROM customer WHERE customer_id = ?";

        try {
            connection = DatabaseConnectionPool.getConnection();
            preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setInt(1, id);

            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                customerBean = new CustomerBean();
                customerBean.setCustomerId(id);
                customerBean.setName(resultSet.getString("name"));
                customerBean.setSurname(resultSet.getString("surname"));
                customerBean.setUsername(resultSet.getString("username"));
                customerBean.setEmail(resultSet.getString("email"));
                customerBean.setPhone(resultSet.getString("phone"));
                customerBean.setPswHash(resultSet.getString("psw_hash"));
            }
        } finally {
            if (preparedStatement != null) {
                preparedStatement.close();
            }
            DatabaseConnectionPool.releaseConnection(connection);
        }

        return customerBean;
    }

    @Override
    public synchronized void doSave(CustomerBean customerBean) throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        String sql = "INSERT INTO customer (`name`, surname, username, email, phone, psw_hash) " +
                "VALUES(?, ?, ?, ?, ?, ?)";

        try {
            connection = DatabaseConnectionPool.getConnection();
            preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setString(1, customerBean.getName());
            preparedStatement.setString(2, customerBean.getSurname());
            preparedStatement.setString(3, customerBean.getUsername());
            preparedStatement.setString(4, customerBean.getEmail());
            preparedStatement.setString(5, customerBean.getPhone());
            preparedStatement.setString(6, customerBean.getPswHash());

            preparedStatement.executeUpdate();
            connection.commit();
        } finally {
            if (preparedStatement != null) {
                preparedStatement.close();
            }
            DatabaseConnectionPool.releaseConnection(connection);
        }
    }

    public synchronized boolean checkLogin(String login, String pawHash) throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        String sql = "SELECT psw_hash FROM customer WHERE username = ? OR email = ?";

        try {
            connection = DatabaseConnectionPool.getConnection();
            preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setString(1, login);
            preparedStatement.setString(2, login);

            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                String customerPswHash = resultSet.getString("psw_hash");
                return customerPswHash.equals(pawHash);
            }
        } finally {
            if (preparedStatement != null) {
                preparedStatement.close();
            }
            DatabaseConnectionPool.releaseConnection(connection);
        }

        return false;
    }
}
