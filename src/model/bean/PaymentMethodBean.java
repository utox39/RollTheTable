package model.bean;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import util.ExpireDateDeserializer;

import java.io.Serializable;
import java.util.Date;

//@ nullable_by_default
public class PaymentMethodBean implements Serializable {
    private int paymentMethodId;
    private int customerId;
    private String cardNumber;
    private String owner;
    @JsonDeserialize(using = ExpireDateDeserializer.class)
    private Date expireDate;

    /*@ private normal_behavior
      @ ensures paymentMethodId == 0 && customerId == 0;
      @ ensures cardNumber == null && owner == null && expireDate == null;
      @*/
    public PaymentMethodBean() {}

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
      @ ensures \result == cardNumber;
      @*/
    public String getCardNumber() {
        return cardNumber;
    }

    /*@ private normal_behavior
      @ assignable this.cardNumber;
      @ ensures this.cardNumber == cardNumber;
      @*/
    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    /*@ private normal_behavior
      @ assignable \nothing;
      @ ensures \result == owner;
      @*/
    public String getOwner() {
        return owner;
    }

    /*@ private normal_behavior
      @ assignable this.owner;
      @ ensures this.owner == owner;
      @*/
    public void setOwner(String owner) {
        this.owner = owner;
    }

    /*@ private normal_behavior
      @ assignable \nothing;
      @ ensures \result == expireDate;
      @*/
    public Date getExpireDate() {
        return expireDate;
    }

    /*@ private normal_behavior
      @ assignable this.expireDate;
      @ ensures this.expireDate == expireDate;
      @*/
    public void setExpireDate(Date expireDate) {
        this.expireDate = expireDate;
    }

}
