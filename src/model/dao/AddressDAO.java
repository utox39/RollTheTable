package model.dao;

import db.DatabaseConnectionPool;
import model.idao.AddressModel;
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
        String sql = "SELECT customer_id, `name`, surname, postcode, city, province, country, street_name, street_number, notes, type " +
                "FROM address " +
                "WHERE address_id = ?";

        try {
            connection = DatabaseConnectionPool.getConnection();
            preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setInt(1, id);

            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                addressBean = new AddressBean();
                addressBean.setCustomerId(resultSet.getInt("customer_id"));
                addressBean.setAddressId(id);
                addressBean.setName(resultSet.getString("name"));
                addressBean.setSurname(resultSet.getString("surname"));
                addressBean.setPostcode(resultSet.getString("postcode"));
                addressBean.setCity(resultSet.getString("city"));
                addressBean.setProvince(resultSet.getString("province"));
                addressBean.setCountry(resultSet.getString("country"));
                addressBean.setStreetName(resultSet.getString("street_name"));
                addressBean.setStreetNumber(resultSet.getString("street_number"));
                addressBean.setNotes(resultSet.getString("notes"));
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

    public synchronized List<AddressBean> doRetrieveAllByCustomerId(int customerId) throws SQLException {
        Connection connection = null;
        AddressBean addressBean = null;

        ArrayList<AddressBean> addressList = new ArrayList<>();

        PreparedStatement preparedStatement = null;
        String sql = "SELECT address_id, `name`, surname, postcode, city, province, country, street_name, street_number, notes, type " +
                "FROM address " +
                "WHERE customer_id = ?";

        try {
            connection = DatabaseConnectionPool.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, customerId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                addressBean = new AddressBean();
                addressBean.setCustomerId(customerId);
                addressBean.setAddressId(resultSet.getInt("address_id"));
                addressBean.setName(resultSet.getString("name"));
                addressBean.setSurname(resultSet.getString("surname"));
                addressBean.setPostcode(resultSet.getString("postcode"));
                addressBean.setCity(resultSet.getString("city"));
                addressBean.setProvince(resultSet.getString("province"));
                addressBean.setCountry(resultSet.getString("country"));
                addressBean.setStreetName(resultSet.getString("street_name"));
                addressBean.setStreetNumber(resultSet.getString("street_number"));
                addressBean.setNotes(resultSet.getString("notes"));
                addressBean.setType(resultSet.getString("type"));

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

        String sql = "INSERT INTO address (customer_id, `name`, surname, postcode, city, province, country, street_name, street_number, notes, type) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try {
            connection = DatabaseConnectionPool.getConnection();
            preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setInt(1, addressBean.getCustomerId());
            preparedStatement.setString(2, addressBean.getName());
            preparedStatement.setString(3, addressBean.getSurname());
            preparedStatement.setString(4, addressBean.getPostcode());
            preparedStatement.setString(5, addressBean.getCity());
            preparedStatement.setString(6, addressBean.getProvince());
            preparedStatement.setString(7, addressBean.getCountry());
            preparedStatement.setString(8, addressBean.getStreetName());
            preparedStatement.setString(9, addressBean.getStreetNumber());
            preparedStatement.setString(10, addressBean.getNotes());
            preparedStatement.setString(11, addressBean.getType());

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
