package model.bean;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/*@ nullable_by_default @*/
public class OrdersBean implements Serializable {

    /*@ nullable_by_default @*/
    public class PurchaseInfo implements Serializable {
        private int quantity;
        private double priceAtPurchase;

        /*@ private normal_behavior
          @ assignable \nothing;
          @ ensures this.quantity == quantity;
          @ ensures this.priceAtPurchase == priceAtPurchase || (Double.isNaN(this.priceAtPurchase) && Double.isNaN(priceAtPurchase));
          @*/
        public PurchaseInfo(int quantity, double priceAtPurchase) {
            this.quantity = quantity;
            this.priceAtPurchase = priceAtPurchase;
        }

        /*@ private normal_behavior
          @ assignable \nothing;
          @ ensures \result == quantity;
          @*/
        public int getQuantity() {
            return quantity;
        }

        /*@ private normal_behavior
          @ assignable \nothing;
          @ ensures \result == priceAtPurchase || (Double.isNaN(\result) && Double.isNaN(priceAtPurchase));
          @*/
        public double getPriceAtPurchase() {
            return priceAtPurchase;
        }

        /*@ private normal_behavior
          @ assignable this.quantity;
          @ ensures this.quantity == quantity;
          @*/
        public void setQuantity(int quantity) {
            this.quantity = quantity;
        }

        /*@ private normal_behavior
          @ assignable this.priceAtPurchase;
          @ ensures this.priceAtPurchase == priceAtPurchase || (Double.isNaN(this.priceAtPurchase) && Double.isNaN(priceAtPurchase));
          @*/
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
    /*@ non_null @*/
    private Map<ProductBean, PurchaseInfo> products = new ConcurrentHashMap<>();

    public OrdersBean() {
    }

    /*@ private normal_behavior
      @ assignable \nothing;
      @ ensures \result == orderId;
      @*/
    public int getOrderId() {
        return orderId;
    }

    /*@ private normal_behavior
      @ assignable this.orderId;
      @ ensures this.orderId == orderId;
      @*/
    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    /*@ private normal_behavior
      @ assignable \nothing;
      @ ensures \result == customerId;
      @*/
    public int getCustomerId() {
        return customerId;
    }

    /*@ private normal_behavior
      @ assignable this.customerId;
      @ ensures this.customerId == customerId;
      @*/
    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    /*@ private normal_behavior
      @ assignable \nothing;
      @ ensures \result == shippingPrice || (Double.isNaN(\result) && Double.isNaN(shippingPrice));
      @*/
    public double getShippingPrice() {
        return shippingPrice;
    }

    /*@ private normal_behavior
      @ assignable this.shippingPrice;
      @ ensures this.shippingPrice == shippingPrice || (Double.isNaN(this.shippingPrice) && Double.isNaN(shippingPrice));
      @*/
    public void setShippingPrice(double shippingPrice) {
        this.shippingPrice = shippingPrice;
    }

    /*@ private normal_behavior
      @ assignable \nothing;
      @ ensures \result == purchaseDate;
      @*/
    public Date getPurchaseDate() {
        return purchaseDate;
    }

    /*@ private normal_behavior
      @ assignable this.purchaseDate;
      @ ensures this.purchaseDate == purchaseDate;
      @*/
    public void setPurchaseDate(Date purchaseDate) {
        this.purchaseDate = purchaseDate;
    }

    /*@ private normal_behavior
      @ assignable \nothing;
      @ ensures \result == paymentMethodId;
      @*/
    public int getPaymentMethodId() {
        return paymentMethodId;
    }

    /*@ private normal_behavior
      @ assignable this.paymentMethodId;
      @ ensures this.paymentMethodId == paymentMethodId;
      @*/
    public void setPaymentMethodId(int paymentMethodId) {
        this.paymentMethodId = paymentMethodId;
    }

    /*@ private normal_behavior
      @ assignable \nothing;
      @ ensures \result == shippingAddressId;
      @*/
    public int getShippingAddressId() {
        return shippingAddressId;
    }

    /*@ private normal_behavior
      @ assignable this.shippingAddressId;
      @ ensures this.shippingAddressId == shippingAddressId;
      @*/
    public void setShippingAddressId(int shippingAddressId) {
        this.shippingAddressId = shippingAddressId;
    }

    /*@ private normal_behavior
      @ assignable \nothing;
      @ ensures \result == billingAddressId;
      @*/
    public int getBillingAddressId() {
        return billingAddressId;
    }

    /*@ private normal_behavior
      @ assignable this.billingAddressId;
      @ ensures this.billingAddressId == billingAddressId;
      @*/
    public void setBillingAddressId(int billingAddressId) {
        this.billingAddressId = billingAddressId;
    }

    /*@ private normal_behavior
      @ assignable \nothing;
      @ ensures \result == products;
      @*/
    public Map<ProductBean, PurchaseInfo> getProducts() {
        return products;
    }
    /*@ private normal_behavior
      @ requires productBean != null && quantity > 0 && priceAtPurchase >= 0;
      @ ensures products.get(productBean).quantity == quantity;
      @ ensures products.get(productBean).priceAtPurchase == priceAtPurchase;
      @*/
    public void addProducts(ProductBean productBean, int quantity, double priceAtPurchase) {
        PurchaseInfo purchaseInfo = new PurchaseInfo(quantity, priceAtPurchase);
        products.put(productBean, purchaseInfo);
    }
}

