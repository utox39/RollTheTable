package model.bean;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class OrdersBean implements Serializable {

    public class PurchaseInfo implements Serializable {
        private int quantity;
        private double priceAtPurchase;

        public PurchaseInfo(int quantity, double priceAtPurchase) {
            this.quantity = quantity;
            this.priceAtPurchase = priceAtPurchase;
        }

        public int getQuantity() {
            return quantity;
        }

        public double getPriceAtPurchase() {
            return priceAtPurchase;
        }

        public void setQuantity(int quantity) {
            this.quantity = quantity;
        }

        public void setPriceAtPurchase(double priceAtPurchase) {
            this.priceAtPurchase = priceAtPurchase;
        }
    }

    private int orderId;
    private int customerId;
    private int paymentMethodId;
    private int shippingAddressId;
    private int billingAddressId;
    private Date purchaseDate;
    private double shippingPrice;
    private Map<ProductBean, PurchaseInfo> products = new ConcurrentHashMap<>();

    public OrdersBean() {
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public double getShippingPrice() {
        return shippingPrice;
    }

    public void setShippingPrice(double shippingPrice) {
        this.shippingPrice = shippingPrice;
    }

    public Date getPurchaseDate() {
        return purchaseDate;
    }

    public void setPurchaseDate(Date purchaseDate) {
        this.purchaseDate = purchaseDate;
    }

    public int getPaymentMethodId() {
        return paymentMethodId;
    }

    public void setPaymentMethodId(int paymentMethodId) {
        this.paymentMethodId = paymentMethodId;
    }

    public int getShippingAddressId() {
        return shippingAddressId;
    }

    public void setShippingAddressId(int shippingAddressId) {
        this.shippingAddressId = shippingAddressId;
    }

    public int getBillingAddressId() {
        return billingAddressId;
    }

    public void setBillingAddressId(int billingAddressId) {
        this.billingAddressId = billingAddressId;
    }

    public Map<ProductBean, PurchaseInfo> getProducts() {
        return products;
    }

    public void addProducts(ProductBean productBean, int quantity, double priceAtPurchase) {
        PurchaseInfo purchaseInfo = new PurchaseInfo(quantity, priceAtPurchase);
        products.put(productBean, purchaseInfo);
    }
}

