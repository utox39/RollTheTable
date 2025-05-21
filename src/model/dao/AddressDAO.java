package model.dao;

import db.DatabaseConnectionPool;
import model.AddressModel;
import model.bean.AddressBean;

import java.sql.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AddressDAO implements AddressModel {
    public synchronized AddressBean doRetrieveById(int id) throws SQLException {
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

    public synchronized List<AddressBean> doRetrieveAllByCustomerId(int id) throws SQLException {
        Connection connection = null;
        AddressBean addressBean = null;

        ArrayList<AddressBean> addressList = new ArrayList<>();

        PreparedStatement preparedStatement = null;
        String getAddressId = "SELECT address FROM uses WHERE customer = ?";

        try {
            connection = DatabaseConnectionPool.getConnection();
            preparedStatement = connection.prepareStatement(getAddressId);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                addressBean = doRetrieveById(resultSet.getInt("address"));
                addressList.add(addressBean);
            }
        } finally {
            if (preparedStatement != null) {
                preparedStatement.close();
            }

            DatabaseConnectionPool.releaseConnection(connection);
        }

        return addressList;
    }

    public synchronized void doSave(AddressBean addressBean) throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        String sql = "INSERT INTO address (postcode, city, province, country, street_name, street_number, type) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?)";

        try {
            connection = DatabaseConnectionPool.getConnection();
            preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setString(1, addressBean.getPostcode());
            preparedStatement.setString(2, addressBean.getCity());
            preparedStatement.setString(3, addressBean.getProvince());
            preparedStatement.setString(4, addressBean.getCountry());
            preparedStatement.setString(5, addressBean.getStreetName());
            preparedStatement.setString(6, addressBean.getStreetNumber());
            preparedStatement.setString(7, addressBean.getType());

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
