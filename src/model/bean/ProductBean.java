package model.bean;

import java.io.Serializable;

/*@ nullable_by_default @*/
public class ProductBean implements Serializable {

    public enum SaleStatus {
        ON_SALE,
        OUT_OF_STOCK,
        NOT_AVAILABLE,
        NO_LONGER_ON_SALE,
    }

    /*@ spec_public @*/
    private String productId;
    private String name;
    private double purchasePrice;
    private double iva;
    private double salePrice;
    private String imagesPath;
    private String description;
    private String vendor;
    private int stockQuantity;
    private double discount;
    private String category;
    private int minimumAge;
    private String barcode;
    private SaleStatus saleStatus;

    public ProductBean(){}

    /*@ public normal_behavior
      @ assignable \nothing;
      @ ensures \result == productId;
      @*/
    public String getProductId() {
        return productId;
    }

    /*@ private normal_behavior
      @ assignable this.productId;
      @ ensures this.productId == productId;
      @*/
    public void setProductId(String productId) {
        this.productId = productId;
    }

    /*@ private normal_behavior
      @ assignable \nothing;
      @ ensures \result == name;
      @*/
    public String getName() {
        return name;
    }

    /*@ private normal_behavior
      @ assignable this.name;
      @ ensures this.name == name;
      @*/
    public void setName(String name) {
        this.name = name;
    }

    /*@ private normal_behavior
      @ assignable \nothing;
      @ ensures \result == purchasePrice || (Double.isNaN(\result) && Double.isNaN(purchasePrice));
      @*/
    public double getPurchasePrice() {
        return purchasePrice;
    }

    /*@ private normal_behavior
      @ assignable this.purchasePrice;
      @ ensures this.purchasePrice == purchasePrice || (Double.isNaN(this.purchasePrice) && Double.isNaN(purchasePrice));
      @*/
    public void setPurchasePrice(double purchasePrice) {
        this.purchasePrice = purchasePrice;
    }

    /*@ private normal_behavior
      @ assignable \nothing;
      @ ensures \result == iva || (Double.isNaN(\result) && Double.isNaN(iva));
      @*/
    public double getIva() {
        return iva;
    }

    /*@ private normal_behavior
      @ assignable this.iva;
      @ ensures this.iva == iva || (Double.isNaN(this.iva) && Double.isNaN(iva));
      @*/
    public void setIva(double iva) {
        this.iva = iva;
    }

    /*@ private normal_behavior
      @ assignable \nothing;
      @ ensures \result == salePrice || (Double.isNaN(\result) && Double.isNaN(salePrice));
      @*/
    public double getSalePrice() {
        return salePrice;
    }

    /*@ private normal_behavior
      @ assignable this.salePrice;
      @ ensures this.salePrice == salePrice || (Double.isNaN(this.salePrice) && Double.isNaN(salePrice));
      @*/
    public void setSalePrice(double salePrice) {
        this.salePrice = salePrice;
    }

    /*@ private normal_behavior
      @ assignable \nothing;
      @ ensures \result == imagesPath;
      @*/
    public String getImagesPath() {
        return imagesPath;
    }

    /*@ private normal_behavior
      @ assignable this.imagesPath;
      @ ensures this.imagesPath == imagesPath;
      @*/
    public void setImagesPath(String imagesPath) {
        this.imagesPath = imagesPath;
    }

    /*@ private normal_behavior
      @ assignable \nothing;
      @ ensures \result == description;
      @*/
    public String getDescription() {
        return description;
    }

    /*@ private normal_behavior
      @ assignable this.description;
      @ ensures this.description == description;
      @*/
    public void setDescription(String description) {
        this.description = description;
    }

    /*@ private normal_behavior
      @ assignable \nothing;
      @ ensures \result == vendor;
      @*/
    public String getVendor() {
        return vendor;
    }

    /*@ private normal_behavior
      @ assignable this.vendor;
      @ ensures this.vendor == vendor;
      @*/
    public void setVendor(String vendor) {
        this.vendor = vendor;
    }

    /*@ private normal_behavior
      @  assignable \nothing;
      @  ensures \result == stockQuantity;
      @*/
    public int getStockQuantity() {
        return stockQuantity;
    }

    /*@ private normal_behavior
      @ assignable this.stockQuantity;
      @ ensures this.stockQuantity == stockQuantity;
      @*/
    public void setStockQuantity(int stockQuantity) {
        this.stockQuantity = stockQuantity;
    }

    /*@ private normal_behavior
      @ assignable \nothing;
      @ ensures \result == discount || (Double.isNaN(\result) && Double.isNaN(discount));
      @*/
    public double getDiscount() {
        return discount;
    }

    /*@ private normal_behavior
      @ assignable this.discount;
      @ ensures this.discount == discount || (Double.isNaN(this.discount) && Double.isNaN(discount));
      @*/
    public void setDiscount(double discount) {
        this.discount = discount;
    }

    /*@ private normal_behavior
      @ assignable \nothing;
      @ ensures \result == category;
      @*/
    public String getCategory() {
        return category;
    }

    /*@ private normal_behavior
      @ assignable this.category;
      @ ensures this.category == category;
      @*/
    public void setCategory(String category) {
        this.category = category;
    }

    /*@ private normal_behavior
      @ assignable \nothing;
      @ ensures \result == minimumAge;
      @*/
    public int getMinimumAge() {
        return minimumAge;
    }

    /*@ private normal_behavior
      @ assignable this.minimumAge;
      @ ensures this.minimumAge == minimumAge;
      @*/
    public void setMinimumAge(int minimumAge) {
        this.minimumAge = minimumAge;
    }

    /*@ private normal_behavior
      @ assignable \nothing;
      @ ensures \result == barcode;
      @*/
    public String getBarcode() {
        return barcode;
    }

    /*@ private normal_behavior
      @ assignable this.barcode;
      @ ensures this.barcode == barcode;
      @*/
    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    /*@ private normal_behavior
      @ assignable \nothing;
      @ ensures \result == saleStatus;
      @*/
    public SaleStatus getSalesStatus() {
        return saleStatus;
    }

    /*@ private normal_behavior
      @ assignable this.saleStatus;
      @ ensures this.saleStatus == saleStatus;
      @*/
    public void setSalesStatus(SaleStatus saleStatus) {
        this.saleStatus = saleStatus;
    }

    @Override
    public String toString() {
        return "ProductBean{" +
                "productId='" + productId + '\'' +
                ", name='" + name + '\'' +
                ", purchasePrice=" + purchasePrice +
                ", iva=" + iva +
                ", salePrice=" + salePrice +
                ", imagesPath='" + imagesPath + '\'' +
                ", description='" + description + '\'' +
                ", vendor='" + vendor + '\'' +
                ", stockQuantity=" + stockQuantity +
                ", discount=" + discount +
                ", category='" + category + '\'' +
                ", minimumAge=" + minimumAge +
                ", barcode='" + barcode + '\'' +
                ", salesStatus=" + saleStatus +
                '}';
    }

}
