package model.bean;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class CartBean implements Serializable {
    private int id;
    private Map<ProductBean, Integer> cart = new HashMap<>();

    public CartBean() {
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

    public void add(ProductBean product, int quantity) {
        boolean alreadyExists = false;

        for (Map.Entry<ProductBean, Integer>productEntry : this.cart.entrySet()) {
            if (productEntry.getKey().getProductId().equals(product.getProductId())) {
                productEntry.setValue(quantity);
                alreadyExists = true;
            }
        }

        if (!alreadyExists) {
            this.cart.put(product, quantity);
        }
    }

    public synchronized void remove(ProductBean product) {
        for (Map.Entry<ProductBean, Integer>productEntry : this.cart.entrySet()) {
            if (productEntry.getKey().getProductId().equals(product.getProductId())) {
                this.cart.remove(productEntry.getKey());
            }
        }
    }


    @Override
    public String toString() {
        return "CartBean{" +
                "cart=" + cart +
                '}';
    }
}
