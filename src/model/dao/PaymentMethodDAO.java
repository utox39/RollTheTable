package model.dao;

import db.DatabaseConnectionPool;
import model.PaymentMethodModel;
import model.bean.PaymentMethodBean;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PaymentMethodDAO implements PaymentMethodModel {
    public PaymentMethodBean doRetriveById(int id) throws SQLException {
        Connection connection = null;
        PaymentMethodBean paymentMethodBean = null;

        PreparedStatement preparedStatement = null;
        String sql = "SELECT payment_method_id, customer_id, card_number, owner, expire_date " +
                "FROM payment_method " +
                "WHERE payment_method_id = ?";

        try {
            connection = DatabaseConnectionPool.getConnection();
            preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setInt(1, id);

            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                paymentMethodBean = new PaymentMethodBean();
                paymentMethodBean.setPaymentMethodId(id);
                paymentMethodBean.setCustomerId(resultSet.getInt("customer_id"));
                paymentMethodBean.setCardNumber(resultSet.getString("card_number"));
                paymentMethodBean.setOwner(resultSet.getString("owner"));
                paymentMethodBean.setExpireDate(resultSet.getDate("expire_date"));
            }
        } finally {
            if (preparedStatement != null) {
                preparedStatement.close();
            }

            DatabaseConnectionPool.releaseConnection(connection);
        }

        return paymentMethodBean;
    }

    public synchronized List<PaymentMethodBean> doRetrieveAllByCustomerId(int id) throws SQLException {
        Connection connection = null;
        PaymentMethodBean paymentMethodBean = null;

        ArrayList<PaymentMethodBean> paymentMethodList = new ArrayList<>();

        PreparedStatement preparedStatement = null;
        String getPaymentMethods = "SELECT payment_method_id, card_number, owner, expire_date " +
                "FROM payment_method " +
                "WHERE customer_id = ?";

        try {
            connection = DatabaseConnectionPool.getConnection();
            preparedStatement = connection.prepareStatement(getPaymentMethods);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                paymentMethodBean = new PaymentMethodBean();
                paymentMethodBean.setPaymentMethodId(resultSet.getInt("payment_method_id"));
                paymentMethodBean.setCustomerId(id);
                paymentMethodBean.setCardNumber(resultSet.getString("card_number"));
                paymentMethodBean.setOwner(resultSet.getString("owner"));
                paymentMethodBean.setExpireDate(resultSet.getDate("expire_date"));

                paymentMethodList.add(paymentMethodBean);
            }
        } finally {
            if (preparedStatement != null) {
                preparedStatement.close();
            }

            DatabaseConnectionPool.releaseConnection(connection);
        }

        return paymentMethodList;
    }

    public void doSave(PaymentMethodBean paymentMethodBean) throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        String sql = "INSERT INTO payment_method(customer_id, card_number, owner, expire_date) "
                + "VALUES(?, ?, ?, ?)";

        try {
            connection = DatabaseConnectionPool.getConnection();
            preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setInt(1, paymentMethodBean.getCustomerId());
            preparedStatement.setString(2, paymentMethodBean.getCardNumber());
            preparedStatement.setString(3, paymentMethodBean.getOwner());

            Date sqlDate = new Date(paymentMethodBean.getExpireDate().getTime());
            preparedStatement.setDate(4, sqlDate);

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
