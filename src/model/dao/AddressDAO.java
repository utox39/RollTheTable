package model.dao;

import db.DatabaseConnectionPool;
import model.AddressModel;
import model.bean.AddressBean;

import java.sql.*;
import java.sql.SQLException;

public class AddressDAO implements AddressModel {
    public synchronized AddressBean doRetriveById(int id) throws SQLException {
        Connection connection = null;
        AddressBean addressBean = null;

        PreparedStatement preparedStatement = null;
        String sql = "SELECT address_id, postcode, city, province, country, street_name, street_number, type " +
                "FROM address " +
                "WHERE address_id = ?";

        try {
            connection = DatabaseConnectionPool.getConnection();
            preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setInt(1, id);

            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                addressBean = new AddressBean();
                addressBean.setAddressId(resultSet.getInt("address_id"));
                addressBean.setPostcode(resultSet.getString("postcode"));
                addressBean.setCity(resultSet.getString("city"));
                addressBean.setProvince(resultSet.getString("province"));
                addressBean.setCountry(resultSet.getString("country"));
                addressBean.setStreetName(resultSet.getString("street_name"));
                addressBean.setStreetNumber(resultSet.getString("street_number"));
                addressBean.setType(resultSet.getString("type"));
            }
        } finally {
            if (preparedStatement != null) {
                preparedStatement.close();
            }

            DatabaseConnectionPool.releaseConnection(connection);
        }

        return addressBean;
    }

    public synchronized void doSave(AddressBean customerBean) throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        String sql = "INSERT INTO address (postcode, city, province, country, street_name, street_number, type) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?)";

        try {
            connection = DatabaseConnectionPool.getConnection();
            preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setString(1, customerBean.getPostcode());
            preparedStatement.setString(2, customerBean.getCity());
            preparedStatement.setString(3, customerBean.getProvince());
            preparedStatement.setString(4, customerBean.getCountry());
            preparedStatement.setString(5, customerBean.getStreetName());
            preparedStatement.setString(6, customerBean.getStreetNumber());
            preparedStatement.setString(7, customerBean.getType());

            preparedStatement.executeUpdate();
            connection.commit();
        } finally {
            if (preparedStatement != null) {
                preparedStatement.close();
            }
            DatabaseConnectionPool.releaseConnection(connection);
        }
    }
}
