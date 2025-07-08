package model;

import model.bean.CartBean;
import model.bean.CustomerBean;
import model.bean.ProductBean;

import java.sql.SQLException;

public interface CartModel {
    public void addToCart(CustomerBean customer, CartBean cart) throws SQLException;
    public CartBean retrieveCart(CustomerBean customer) throws SQLException;
    public void removeFromCart(CartBean cart, ProductBean product) throws SQLException;
    public void doUpdateQuantity(CartBean cart, ProductBean product, int quantity) throws SQLException;
}
