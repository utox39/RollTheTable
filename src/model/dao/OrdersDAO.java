package model.dao;

import db.DatabaseConnectionPool;
import model.OrdersModel;
import model.bean.OrdersBean;
import model.bean.ProductBean;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class OrdersDAO implements OrdersModel {
    public OrdersBean doRetriveById(int id) throws SQLException {
        Connection connection = null;
        OrdersBean ordersBean;

        PreparedStatement ps1 = null;
        PreparedStatement ps2 = null;

        String getOrderQuery = "SELECT order_id, customer_id, payment_method_id, shipping_address_id, billing_address_id, purchase_date, shipping_price " +
                "FROM purchase " +
                "WHERE order_id = ?";

        String getOrderProductsQuery = "SELECT order_id, product_id, quantity, price_at_purchase " +
                "FROM purchase_items " +
                "WHERE order_id = ?";

        try {
            connection = DatabaseConnectionPool.getConnection();
            ps1 = connection.prepareStatement(getOrderQuery);
            ps2 = connection.prepareStatement(getOrderProductsQuery);

            ordersBean = new OrdersBean();

            ps1.setInt(1, id);
            ResultSet rs1 = ps1.executeQuery();
            if (rs1.next()) {
                ordersBean.setOrderId(rs1.getInt("order_id"));
                ordersBean.setCustomerId(rs1.getInt("customer_id"));
                ordersBean.setPaymentMethodId(rs1.getInt("payment_method_id"));
                ordersBean.setShippingAddressId(rs1.getInt("shipping_address_id"));
                ordersBean.setBillingAddressId(rs1.getInt("billing_address_id"));
                ordersBean.setPurchaseDate(rs1.getDate("purchase_date"));
                ordersBean.setShippingPrice(rs1.getDouble("shipping_price"));
            }

            ps2.setInt(1, id);
            ResultSet rs2 = ps2.executeQuery();
            while (rs2.next()) {
                ProductBean productBean;
                ProductDAO productDAO = new ProductDAO();
                productBean = productDAO.doRetrieveById(rs2.getString("product_id"));
                ordersBean.addProducts(productBean,
                        rs2.getInt("quantity"),
                        rs2.getDouble("price_at_purchase")
                );
            }
        } finally {
            if (ps1 != null) {
                ps1.close();
            }

            if (ps2 != null) {
                ps2.close();
            }

            DatabaseConnectionPool.releaseConnection(connection);
        }

        return ordersBean;
    }

    public void doSave(OrdersBean ordersBean) throws SQLException {
        Connection connection = null;
        PreparedStatement ps1 = null;
        PreparedStatement ps2 = null;

        String insertPurchaseQuery = "INSERT INTO purchase (customer_id, payment_method_id, shipping_address_id, billing_address_id, shipping_price) "
                + "VALUES (?, ?, ?, ?, ?)";

        String insertProductsQuery = "INSERT INTO purchase_items (order_id, product_id, quantity, price_at_purchase) "
                + "VALUES (?, ?, ?, ?)";

        try {
            connection = DatabaseConnectionPool.getConnection();
            ps1 = connection.prepareStatement(insertPurchaseQuery, Statement.RETURN_GENERATED_KEYS);
            ps2 = connection.prepareStatement(insertProductsQuery);

            ps1.setInt(1, ordersBean.getCustomerId());
            ps1.setInt(2, ordersBean.getPaymentMethodId());
            ps1.setInt(3, ordersBean.getShippingAddressId());
            ps1.setDouble(4, ordersBean.getBillingAddressId());
            ps1.setDouble(5, ordersBean.getShippingPrice());
            ps1.executeUpdate();

            ResultSet generatedKeys = ps1.getGeneratedKeys();
            int orderId;
            if (generatedKeys.next()) {
                orderId = generatedKeys.getInt(1);
            } else {
                throw new SQLException("Order creation failed, no ID generated.");
            }

            Map<ProductBean, OrdersBean.PurchaseInfo> products = ordersBean.getProducts();
            for (Map.Entry<ProductBean, OrdersBean.PurchaseInfo> product : products.entrySet()) {
                ps2.setInt(1, orderId);
                ps2.setInt(2, Integer.parseInt(product.getKey().getProductId()));
                ps2.setInt(3, product.getValue().getQuantity());
                ps2.setDouble(4, product.getValue().getPriceAtPurchase());

                ps2.addBatch();
                ps2.clearParameters();
            }
            ps2.executeBatch();

            connection.commit();
        } finally {
            if (ps1 != null) {
                ps1.close();
            }

            if (ps2 != null) {
                ps2.close();
            }

            DatabaseConnectionPool.releaseConnection(connection);
        }
    }

    public List<OrdersBean> doRetrieveAll() throws SQLException {
        ArrayList<OrdersBean> ordersList = new ArrayList<>();

        Connection connection = null;
        OrdersBean ordersBean;

        PreparedStatement ps1 = null;
        PreparedStatement ps2 = null;

        String getOrderQuery = "SELECT order_id, customer_id, payment_method_id, shipping_address_id, billing_address_id, purchase_date, shipping_price " +
                "FROM purchase";

        String getOrderProductsQuery = "SELECT order_id, product_id, quantity, price_at_purchase " +
                "FROM purchase_items " +
                "WHERE order_id = ?";

        try {
            connection = DatabaseConnectionPool.getConnection();
            ps1 = connection.prepareStatement(getOrderQuery);
            ps2 = connection.prepareStatement(getOrderProductsQuery);

            ResultSet rs1 = ps1.executeQuery();
            while (rs1.next()) {
                ordersBean = new OrdersBean();
                ordersBean.setOrderId(rs1.getInt("order_id"));
                ordersBean.setOrderId(rs1.getInt("order_id"));
                ordersBean.setCustomerId(rs1.getInt("customer_id"));
                ordersBean.setPaymentMethodId(rs1.getInt("payment_method_id"));
                ordersBean.setShippingAddressId(rs1.getInt("shipping_address_id"));
                ordersBean.setBillingAddressId(rs1.getInt("billing_address_id"));
                ordersBean.setPurchaseDate(rs1.getDate("purchase_date"));
                ordersBean.setShippingPrice(rs1.getDouble("shipping_price"));

                ps2.setInt(1, rs1.getInt("order_id"));
                ResultSet rs2 = ps2.executeQuery();
                while (rs2.next()) {
                    ProductBean productBean;
                    ProductDAO productDAO = new ProductDAO();
                    productBean = productDAO.doRetrieveById(rs2.getString("product_id"));
                    ordersBean.addProducts(productBean,
                            rs2.getInt("quantity"),
                            rs2.getDouble("price_at_purchase")
                    );
                }

                ordersList.add(ordersBean);
            }
        } finally {
            if (ps1 != null) {
                ps1.close();
            }

            if (ps2 != null) {
                ps2.close();
            }

            DatabaseConnectionPool.releaseConnection(connection);
        }

        return ordersList;
    }
    public List<OrdersBean> doRetrieveAllByCustomerId(int id) throws SQLException {
        ArrayList<OrdersBean> ordersList = new ArrayList<>();

        Connection connection = null;
        OrdersBean ordersBean;

        PreparedStatement ps1 = null;
        PreparedStatement ps2 = null;

        String getOrderQuery = "SELECT order_id, customer_id, payment_method_id, shipping_address_id, billing_address_id, purchase_date, shipping_price " +
                "FROM purchase " +
                "WHERE customer_id = ?";

        String getOrderProductsQuery = "SELECT order_id, product_id, quantity, price_at_purchase " +
                "FROM purchase_items " +
                "WHERE order_id = ?";

        try {
            connection = DatabaseConnectionPool.getConnection();
            ps1 = connection.prepareStatement(getOrderQuery);
            ps2 = connection.prepareStatement(getOrderProductsQuery);

            ps2.setInt(1, id);
            ResultSet rs1 = ps1.executeQuery();
            while (rs1.next()) {
                ordersBean = new OrdersBean();
                ordersBean.setOrderId(rs1.getInt("order_id"));
                ordersBean.setOrderId(rs1.getInt("order_id"));
                ordersBean.setCustomerId(rs1.getInt("customer_id"));
                ordersBean.setPaymentMethodId(rs1.getInt("payment_method_id"));
                ordersBean.setShippingAddressId(rs1.getInt("shipping_address_id"));
                ordersBean.setBillingAddressId(rs1.getInt("billing_address_id"));
                ordersBean.setPurchaseDate(rs1.getDate("purchase_date"));
                ordersBean.setShippingPrice(rs1.getDouble("shipping_price"));

                ps2.setInt(1, rs1.getInt("order_id"));
                ResultSet rs2 = ps2.executeQuery();
                while (rs2.next()) {
                    ProductBean productBean;
                    ProductDAO productDAO = new ProductDAO();
                    productBean = productDAO.doRetrieveById(rs2.getString("product_id"));
                    ordersBean.addProducts(productBean,
                            rs2.getInt("quantity"),
                            rs2.getDouble("price_at_purchase")
                    );
                }

                ordersList.add(ordersBean);
            }
        } finally {
            if (ps1 != null) {
                ps1.close();
            }

            if (ps2 != null) {
                ps2.close();
            }

            DatabaseConnectionPool.releaseConnection(connection);
        }

        return ordersList;
    }
}
