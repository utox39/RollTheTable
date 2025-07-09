package model.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import db.DatabaseConnectionPool;
import model.CustomerModel;
import model.bean.CustomerBean;

public class CustomerDAO implements CustomerModel {
    public synchronized List<CustomerBean> doRetrieveAll() throws SQLException {
        Connection connection = null;
        CustomerBean customerBean;
        List<CustomerBean> customers = null;

        PreparedStatement preparedStatement = null;
        String sql = "SELECT `name`, surname, username, email, phone, psw_hash FROM customer";

        try {
            connection = DatabaseConnectionPool.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            customers = new ArrayList<>();


            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                customerBean = new CustomerBean();
                customerBean.setName(resultSet.getString("name"));
                customerBean.setSurname(resultSet.getString("surname"));
                customerBean.setUsername(resultSet.getString("username"));
                customerBean.setEmail(resultSet.getString("email"));
                customerBean.setPhone(resultSet.getString("phone"));
                customerBean.setPswHash(resultSet.getString("psw_hash"));
                customers.add(customerBean);
            }
        } finally {
            if (preparedStatement != null) {
                preparedStatement.close();
            }
            DatabaseConnectionPool.releaseConnection(connection);
        }

        return customers;
    }

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

    public synchronized void doUpdate(CustomerBean customerBean) throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        String sql = "UPDATE customer SET `name` = ?, surname = ?, username = ?, email = ?, phone = ?, psw_hash = ? " +
                "WHERE customer_id = ?";

        try {
            connection = DatabaseConnectionPool.getConnection();
            preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setString(1, customerBean.getName());
            preparedStatement.setString(2, customerBean.getSurname());
            preparedStatement.setString(3, customerBean.getUsername());
            preparedStatement.setString(4, customerBean.getEmail());
            preparedStatement.setString(5, customerBean.getPhone());
            preparedStatement.setString(6, customerBean.getPswHash());
            preparedStatement.setInt(7, customerBean.getCustomerId()); // Assuming customerId is used for updating

            preparedStatement.executeUpdate();
            connection.commit();
        } finally {
            if (preparedStatement != null) {
                preparedStatement.close();
            }
            DatabaseConnectionPool.releaseConnection(connection);
        }
    }
    public synchronized CustomerBean authenticate(String login, String pswHash) throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        String sql = "SELECT customer_id, psw_hash FROM customer WHERE (username = ? OR email = ?)";

        try {
            connection = DatabaseConnectionPool.getConnection();
            preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setString(1, login);
            preparedStatement.setString(2, login);

            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                int id = resultSet.getInt("customer_id");
                String customerPswHash = resultSet.getString("psw_hash");

                if (customerPswHash.equals(pswHash)) {
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

    public synchronized boolean doesEmailExists(String email) throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        String checkEmailQuery = "SELECT 1 FROM customer WHERE email = ? LIMIT 1";

        try {
            connection = DatabaseConnectionPool.getConnection();
            preparedStatement = connection.prepareStatement(checkEmailQuery);

            preparedStatement.setString(1, email);

            ResultSet resultSet = preparedStatement.executeQuery();
            return resultSet.next();
        } finally {
            if (preparedStatement != null) {
                preparedStatement.close();
            }

            DatabaseConnectionPool.releaseConnection(connection);
        }
    }
}
