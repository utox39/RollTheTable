package model.dao;

import db.DatabaseConnectionPool;
import model.bean.CartBean;
import model.bean.CustomerBean;
import model.bean.ProductBean;
import util.ServerLogger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;


//TODO:
// OP:
// insert cart (1 time) -> insert ignores values
// insert cart_contains
// OP:
// remove cart_contains


public class CartDAO {

    public synchronized void addToCart(CustomerBean customer, CartBean cart) throws SQLException {
        String cartQuery = "insert into cart(customer_id) values(?) on duplicate key update customer_id = customer_id";
        String cartContainsQuery = "insert into cart_contains values(?, ? , ?) on duplicate key update quantity = ?)";
        PreparedStatement stmt1 = null;
        PreparedStatement stmt2 = null;
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
       return null;
    }

    public synchronized void removeFromCart(CartBean cart) throws SQLException {

    }


}
