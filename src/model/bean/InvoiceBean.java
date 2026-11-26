package model.bean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/*@ nullable_by_default @*/
public class InvoiceBean implements Serializable {
    private int invoiceId;
    private int customerId;
    private int purchaseId;
    private Date invoiceDate;
    private BigDecimal iva;
    private BigDecimal taxable;
    private BigDecimal tax;
    private String notes;

    // Optional: add company and client name/address for PDF
    private String clientName;
    private String clientAddress;
    private String clientVat;

    // Getters and Setters...

    /*@ private normal_behavior
      @ assignable \nothing;
      @ ensures \result == invoiceId;
      @*/
    public int getInvoiceId() {
        return invoiceId;
    }

    /*@ private normal_behavior
      @ assignable this.invoiceId;
      @ ensures this.invoiceId == invoiceId;
      @*/
    public void setInvoiceId(int invoiceId) {
        this.invoiceId = invoiceId;
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
      @ ensures \result == purchaseId;
      @*/
    public int getPurchaseId() {
        return purchaseId;
    }

    /*@ private normal_behavior
      @ assignable this.purchaseId;
      @ ensures this.purchaseId == purchaseId;
      @*/
    public void setPurchaseId(int purchaseId) {
        this.purchaseId = purchaseId;
    }

    /*@ private normal_behavior
      @ assignable \nothing;
      @ ensures \result == invoiceDate;
      @*/
    public Date getInvoiceDate() {
        return invoiceDate;
    }

    /*@ private normal_behavior
      @ assignable this.invoiceDate;
      @ ensures this.invoiceDate == invoiceDate;
      @*/
    public void setInvoiceDate(Date invoiceDate) {
        this.invoiceDate = invoiceDate;
    }

    /*@ private normal_behavior
      @ assignable \nothing;
      @ ensures \result == iva;
      @*/
    public BigDecimal getIva() {
        return iva;
    }

    /*@ private normal_behavior
      @ assignable this.iva;
      @ ensures this.iva == iva;
      @*/
    public void setIva(BigDecimal iva) {
        this.iva = iva;
    }

    /*@ private normal_behavior
      @ assignable \nothing;
      @ ensures \result == taxable;
      @*/
    public BigDecimal getTaxable() {
        return taxable;
    }

    /*@ private normal_behavior
      @ assignable this.taxable;
      @ ensures this.taxable == taxable;
      @*/
    public void setTaxable(BigDecimal taxable) {
        this.taxable = taxable;
    }

    /*@ private normal_behavior
      @ assignable \nothing;
      @ ensures \result == tax;
      @*/
    public BigDecimal getTax() {
        return tax;
    }

    /*@ private normal_behavior
      @ assignable this.tax;
      @ ensures this.tax == tax;
      @*/
    public void setTax(BigDecimal tax) {
        this.tax = tax;
    }

    /*@ private normal_behavior
      @ assignable \nothing;
      @ ensures \result == notes;
      @*/
    public String getNotes() {
        return notes;
    }

    /*@ private normal_behavior
      @ assignable this.notes;
      @ ensures this.notes == notes;
      @*/
    public void setNotes(String notes) {
        this.notes = notes;
    }

    /*@ private normal_behavior
      @ assignable \nothing;
      @ ensures \result == clientName;
      @*/
    public String getClientName() {
        return clientName;
    }

    /*@ private normal_behavior
      @ assignable this.clientName;
      @ ensures this.clientName == clientName;
      @*/
    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    /*@ private normal_behavior
      @ assignable \nothing;
      @ ensures \result == clientAddress;
      @*/
    public String getClientAddress() {
        return clientAddress;
    }

    /*@ private normal_behavior
      @ assignable this.clientAddress;
      @ ensures this.clientAddress == clientAddress;
      @*/
    public void setClientAddress(String clientAddress) {
        this.clientAddress = clientAddress;
    }

    /*@ private normal_behavior
      @ assignable \nothing;
      @ ensures \result == clientVat;
      @*/
    public String getClientVat() {
        return clientVat;
    }

    /*@ private normal_behavior
      @ assignable this.clientVat;
      @ ensures this.clientVat == clientVat;
      @*/
    public void setClientVat(String clientVat) {
        this.clientVat = clientVat;
    }
}
