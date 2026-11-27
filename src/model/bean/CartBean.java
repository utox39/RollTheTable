package model.bean;

import java.io.Serializable;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/*@ nullable_by_default @*/
public class CartBean implements Serializable {
    private int id;
    private Map<ProductBean, Integer> cart = new ConcurrentHashMap<>();

    public CartBean() {
    }

    // Getter
    /*@ private normal_behavior
      @ assignable \nothing;
      @ ensures \result == cart;
      @*/
    public Map<ProductBean, Integer> getCart() {
        return cart;
    }

    /*@ private normal_behavior
      @ assignable \nothing;
      @ ensures \result == id;
      @*/
    public int getCartId() {
        return id;
    }

    /*@ private behavior
      @ requires cart != null;
      @ ensures \result >= 0;
      @ also
      @ private exceptional_behavior
      @ requires cart == null;
      @ signals_only NullPointerException;
      @*/
    public int getQuantity() {
        int quantity = cart.size();
        if (quantity < 0) {
            throw new IllegalStateException("Cart size cannot be negative");
        }
        return quantity;
    }

    // Setter
    /*@ private normal_behavior
      @ assignable this.id;
      @ ensures this.id == id;
      @*/
    public void setId(int id) {
        this.id = id;
    }

    /*@ private normal_behavior
      @ assignable this.cart;
      @ ensures this.cart == cart;
      @*/
    public void setCart(Map<ProductBean, Integer> cart) {
        this.cart = cart;
    }

    /*@ private behavior
      @ requires cart != null;
      @ assignable \everything;
      @ also
      @ private exceptional_behavior
      @ requires cart == null;
      @ signals_only NullPointerException;
      @*/
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

    /*@ private behavior
      @ requires cart != null;
      @ assignable \everything;
      @ also
      @ private exceptional_behavior
      @ requires cart == null;
      @ signals_only NullPointerException;
      @*/
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

    /*@ private behavior
      @ requires cart != null;
      @ assignable \everything;
      @ also
      @ private exceptional_behavior
      @ requires cart == null;
      @ signals_only NullPointerException;
      @*/
    public void remove(ProductBean product) {
        for (Map.Entry<ProductBean, Integer>productEntry : this.cart.entrySet()) {
            if (productEntry.getKey().getProductId().equals(product.getProductId())) {
                this.cart.remove(productEntry.getKey());
                break;
            }
        }
    }

    /*@ private behavior
      @ requires cart != null;
      @ assignable \everything;
      @ also
      @ private exceptional_behavior
      @ requires cart == null;
      @ signals_only NullPointerException;
      @*/
    public void decreaseQuantity(ProductBean product) {
        for (Map.Entry<ProductBean, Integer>productEntry : this.cart.entrySet()) {
            if (productEntry.getKey().getProductId().equals(product.getProductId())) {
                if ((productEntry.getValue() - 1) == 0) {
                    this.cart.remove(productEntry.getKey());
                } else {
                    productEntry.setValue(productEntry.getValue() - 1);
                }

                break;
            }
        }
    }

    /*@ also
      @ private behavior
      @ ensures \result != null;
      @*/
    @Override
    public String toString() {
        return "CartBean{" +
                "cart=" + cart +
                '}';
    }
}
