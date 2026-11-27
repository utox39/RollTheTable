package model.bean;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

class CartBeanTest {
    private CartBean cart;

    @BeforeEach
    void setUp() {
        cart = new CartBean();
    }

    @Test
    void addNewProductStartsWithQuantityOne() {
        ProductBean product = product("book-1");

        cart.add(product);

        assertEquals(1, cart.getCart().get(product));
        assertEquals(1, cart.getQuantity());
    }

    @Test
    void addProductWithExistingIdIncrementsItsQuantity() {
        ProductBean original = product("book-1");
        cart.add(original);

        cart.add(product("book-1"));

        assertEquals(1, cart.getCart().size());
        assertSame(original, cart.getCart().keySet().iterator().next());
        assertEquals(2, cart.getCart().get(original));
    }

    @Test
    void addWithQuantityReplacesQuantityForExistingProductId() {
        ProductBean original = product("book-1");
        cart.add(original);

        cart.add(product("book-1"), 4);

        assertEquals(1, cart.getCart().size());
        assertEquals(4, cart.getCart().get(original));
    }

    @Test
    void removeAcceptsDifferentProductBeanWithSameId() {
        ProductBean original = product("book-1");
        cart.add(original);

        cart.remove(product("book-1"));

        assertTrue(cart.getCart().isEmpty());
    }

    @Test
    void decreaseQuantityRemovesProductWhenQuantityReachesZeroUsingEquivalentBean() {
        ProductBean original = product("book-1");
        cart.add(original);

        cart.decreaseQuantity(product("book-1"));

        assertTrue(cart.getCart().isEmpty());
    }

    @Test
    void decreaseQuantityReducesQuantityWhenMoreThanOne() {
        ProductBean product = product("book-1");
        cart.add(product, 3);

        cart.decreaseQuantity(product("book-1"));

        assertEquals(2, cart.getCart().get(product));
    }

    @Test
    void removingProductNotInCartDoesNotChangeCart() {
        ProductBean product = product("book-1");
        cart.add(product);

        cart.remove(product("book-2"));

        assertEquals(1, cart.getCart().size());
        assertTrue(cart.getCart().containsKey(product));
        assertFalse(cart.getCart().containsKey(product("book-2")));
    }

    private ProductBean product(String id) {
        ProductBean product = new ProductBean();
        product.setProductId(id);
        return product;
    }
}
