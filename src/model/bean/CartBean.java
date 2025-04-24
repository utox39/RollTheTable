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

    public int getCartId() {
        return id;
    }

    public int getQuantity() {
        return cart.size();
    }

    // Setter
    public void setId(int id) {
        this.id = id;
    }

    public void setCart(Map<ProductBean, Integer> cart) {
        this.cart = cart;
    }

    public void add(ProductBean product) {
//        cart.computeIfPresent(product, (k, v) -> v + 1);
//        cart.computeIfAbsent(product, k -> 1);
        boolean alreadyExists = false;

        for (Map.Entry<ProductBean, Integer>productEntry : this.cart.entrySet()) {
            if (productEntry.getKey().getProductId().equals(product.getProductId())) {
                productEntry.setValue(productEntry.getValue() + 1);
                alreadyExists = true;
            }
        }

        if (!alreadyExists) {
            this.cart.put(product, 1);
        }
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
