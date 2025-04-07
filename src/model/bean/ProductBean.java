package model.bean;

import java.io.Serializable;

public class ProductBean implements Serializable {

    public enum SalesStatus {
        ON_SALE,
        OUT_OF_STOCK,
        NOT_AVAILABLE,
        NO_LONGER_ON_SALE,
    }

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
    private SalesStatus salesStatus;

    public ProductBean(){}

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPurchasePrice() {
        return purchasePrice;
    }

    public void setPurchasePrice(double purchasePrice) {
        this.purchasePrice = purchasePrice;
    }

    public double getIva() {
        return iva;
    }

    public void setIva(double iva) {
        this.iva = iva;
    }

    public double getSalePrice() {
        return salePrice;
    }

    public void setSalePrice(double salePrice) {
        this.salePrice = salePrice;
    }

    public String getImagesPath() {
        return imagesPath;
    }

    public void setImagesPath(String imagesPath) {
        this.imagesPath = imagesPath;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getVendor() {
        return vendor;
    }

    public void setVendor(String vendor) {
        this.vendor = vendor;
    }

    public int getStockQuantity() {
        return stockQuantity;
    }

    public void setStockQuantity(int stockQuantity) {
        this.stockQuantity = stockQuantity;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getMinimumAge() {
        return minimumAge;
    }

    public void setMinimumAge(int minimumAge) {
        this.minimumAge = minimumAge;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public SalesStatus getSalesStatus() {
        return salesStatus;
    }

    public void setSalesStatus(SalesStatus salesStatus) {
        this.salesStatus = salesStatus;
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
                ", salesStatus=" + salesStatus +
                '}';
    }

}
