package model.bean;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class CartBean implements Serializable {
    private int id;
    private Map<ProductBean, Integer> cart = new HashMap<>();

    // Costruttore vuoto
    public CartBean() {
    }

    // Costruttore completo
    public CartBean(Map<ProductBean, Integer> cart) {
        this.cart = cart;
    }

    // Getter
    public Map<ProductBean, Integer> getCart() {
        return cart;
    }

    public int getId() {
        return id;
    }

    // Setter
    public void setCart(Map<ProductBean, Integer> cart) {
        this.cart = cart;
    }

    public void add(ProductBean product) {
        cart.computeIfPresent(product, (k, v) -> v + 1);
        cart.computeIfAbsent(product, k -> 1);
    }

    public void remove(ProductBean product) {
        cart.computeIfPresent(product, (k, v) -> v - 1);
        if (cart.get(product) <= 0) {
            cart.remove(product);
        }
    }


    @Override
    public String toString() {
        return "CartBean{" +
                "cart=" + cart +
                '}';
    }
}
