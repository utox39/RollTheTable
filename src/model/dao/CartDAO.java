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
        String cartContainsQuery = "insert into cart_contains values(?, ? , ?) on duplicate key update quantity = ?)";
        PreparedStatement stmt1;
        PreparedStatement stmt2;
        Connection connection = null;

        try {
            connection = DatabaseConnectionPool.getConnection();

            // insert cart
            stmt1 = connection.prepareStatement(cartQuery);
            stmt1.setInt(1, customer.getCustomerId());
            stmt1.executeQuery();

            // insert cart_contains
            stmt2 = connection.prepareStatement(cartContainsQuery);
            PreparedStatement finalStmt = stmt2;
            cart.getCart().forEach((key, value) -> {
                try {
                    finalStmt.setInt(1, cart.getCartId());
                    finalStmt.setInt(2, customer.getCustomerId());
                    finalStmt.setInt(3, cart.getQuantity());
                    finalStmt.setInt(4, cart.getQuantity());
                    finalStmt.executeQuery();
                } catch (SQLException e) {
                    ServerLogger.severe(CartDAO.class.getName(), "addToCart SQLException: " + e.getMessage());
                }

            });

        } finally {
            DatabaseConnectionPool.releaseConnection(connection);
        }
    }

    public synchronized CartBean retrieveCart(CustomerBean customer) throws SQLException {
        String cartQuery = "SELECT cart_id FROM cart WHERE customer_id = ?";
        String cartContainsQuery = "SELECT product_id FROM cart_contains WHERE cart_id = ?";
        PreparedStatement stmt1;
        PreparedStatement stmt2;
        CartBean cart;
        Connection connection = null;

        try {
            connection = DatabaseConnectionPool.getConnection();

            stmt1 = connection.prepareStatement(cartQuery);
            stmt1.setInt(1, customer.getCustomerId());

            int cartId = -1;

            // assume that only one cart exists for a customer
            var res = stmt1.executeQuery();
            res.next();
            cartId = res.getInt("cart_id");


            stmt2 = connection.prepareStatement(cartContainsQuery);
            stmt2.setInt(1, cartId);
            ResultSet resultSet = stmt2.executeQuery();


            cart = new CartBean();
            cart.setCart(new HashMap<>());
            ProductDAO productDAO = new ProductDAO();

            while (resultSet.next()) {
                cart.add(productDAO.doRetrieveById(resultSet.getString("product_id")));
            }

        } finally {
            DatabaseConnectionPool.releaseConnection(connection);
        }


        return cart;
    }

    public synchronized void removeFromCart(CartBean cart, ProductBean product) throws SQLException {

    }


}
