package model.dao;

import db.DatabaseConnectionPool;
import model.bean.CartBean;
import model.bean.CustomerBean;
import model.bean.ProductBean;
import util.ServerLogger;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;


public class CartDAO {

    public synchronized void addToCart(CustomerBean customer, CartBean cart) throws SQLException {
        String cartQuery = "insert into cart(customer_id) values(?) on duplicate key update customer_id = customer_id";
        String cartContainsQuery = "insert into cart_contains values(?, ? , ?) on duplicate key update quantity = ?";
        PreparedStatement stmt1 = null;
        PreparedStatement stmt2 = null;
        Connection connection = null;

        try {
            connection = DatabaseConnectionPool.getConnection();

            // insert cart
            stmt1 = connection.prepareStatement(cartQuery);
            stmt1.setInt(1, customer.getCustomerId());
            stmt1.executeUpdate();
            connection.commit();

            // insert cart_contains
            stmt2 = connection.prepareStatement(cartContainsQuery);
            PreparedStatement finalStmt = stmt2;
            cart.getCart().forEach((product, quantity) -> {
                try {
                    finalStmt.setInt(1, cart.getCartId());
                    finalStmt.setInt(2, Integer.parseInt(product.getProductId()));
                    finalStmt.setInt(3, quantity);
                    finalStmt.setInt(4, quantity);
                    finalStmt.addBatch();
                    finalStmt.clearParameters();
                } catch (SQLException e) {
                    ServerLogger.severe(CartDAO.class.getName(), "addToCart SQLException: " + e.getMessage());
                }

            });
            finalStmt.executeBatch();
            connection.commit();
        } finally {
            if (stmt1 != null) {
                stmt1.close();
            }

            if (stmt2 != null) {
                stmt2.close();
            }

            DatabaseConnectionPool.releaseConnection(connection);
        }
    }

    public synchronized CartBean retrieveCart(CustomerBean customer) throws SQLException {
        String cartQuery = "SELECT cart_id FROM cart WHERE customer_id = ?";
        String cartContainsQuery = "SELECT product_id, quantity FROM cart_contains WHERE cart_id = ?";
        PreparedStatement stmt1 = null;
        PreparedStatement stmt2 = null;
        CartBean cart;
        Connection connection = null;

        try {
            connection = DatabaseConnectionPool.getConnection();

            stmt1 = connection.prepareStatement(cartQuery);
            stmt1.setInt(1, customer.getCustomerId());

            int cartId = -1;

            // assume that only one cart exists for a customer
            ResultSet res = stmt1.executeQuery();
            res.next();
            cartId = res.getInt("cart_id");

            stmt2 = connection.prepareStatement(cartContainsQuery);
            stmt2.setInt(1, cartId);
            ResultSet resultSet = stmt2.executeQuery();

            cart = new CartBean();
            cart.setCart(new HashMap<>());
            ProductDAO productDAO = new ProductDAO();

            cart.setId(cartId);

            while (resultSet.next()) {
                cart.add(
                        productDAO.doRetrieveById(resultSet.getString("product_id")),
                        resultSet.getInt("quantity")
                );
            }
        } finally {
            if (stmt1 != null) {
                stmt1.close();
            }

            if (stmt2 != null) {
                stmt2.close();
            }

            DatabaseConnectionPool.releaseConnection(connection);
        }

        return cart;
    }

    public synchronized void removeFromCart(CartBean cart, ProductBean product) throws SQLException {

    }
}
