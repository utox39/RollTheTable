package model.bean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

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

    public int getInvoiceId() {
        return invoiceId;
    }

    public void setInvoiceId(int invoiceId) {
        this.invoiceId = invoiceId;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public int getPurchaseId() {
        return purchaseId;
    }

    public void setPurchaseId(int purchaseId) {
        this.purchaseId = purchaseId;
    }

    public Date getInvoiceDate() {
        return invoiceDate;
    }

    public void setInvoiceDate(Date invoiceDate) {
        this.invoiceDate = invoiceDate;
    }

    public BigDecimal getIva() {
        return iva;
    }

    public void setIva(BigDecimal iva) {
        this.iva = iva;
    }

    public BigDecimal getTaxable() {
        return taxable;
    }

    public void setTaxable(BigDecimal taxable) {
        this.taxable = taxable;
    }

    public BigDecimal getTax() {
        return tax;
    }

    public void setTax(BigDecimal tax) {
        this.tax = tax;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public String getClientAddress() {
        return clientAddress;
    }

    public void setClientAddress(String clientAddress) {
        this.clientAddress = clientAddress;
    }

    public String getClientVat() {
        return clientVat;
    }

    public void setClientVat(String clientVat) {
        this.clientVat = clientVat;
    }
}
