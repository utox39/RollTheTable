package model.dao;

import model.bean.ProductBean;
import util.ConnectionUtil;
import util.ServerLogger;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductDAO {

    private static final Connection con = ConnectionUtil.connect();

    private ProductDAO() {
    }

    /**
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

    */

    public static List<ProductBean> getAllProducts() {
        try {
            List <ProductBean> products = new ArrayList<>();
            String sql = "select * from product;";

            ResultSet rs = con.prepareStatement(sql).executeQuery();


            while (rs.next()) {
                ProductBean productBean = new ProductBean();
                productBean.setProductId(rs.getString("product_id"));
                productBean.setName(rs.getString("name"));
                productBean.setCategory(rs.getString("category"));
                productBean.setBarcode(rs.getString("barcode"));
                productBean.setPurchasePrice(rs.getDouble("purchase_price"));
                productBean.setSalePrice(rs.getDouble("sale_price"));
                productBean.setDescription(rs.getString("description"));
                productBean.setStockQuantity(rs.getInt("stock_quantity"));
                productBean.setDiscount(rs.getDouble("discount"));
                productBean.setIva(rs.getDouble("iva"));
                productBean.setImagesPath(rs.getString("images_path"));
                productBean.setSalesStatus(rs.getObject("sale_status", ProductBean.SalesStatus.class));

                products.add(productBean);
            }
            return products;

        } catch (SQLException e) {
            ServerLogger.severe(ProductDAO.class.getName(), "getAllProducts SQLException: " + e.getMessage());
        }

        return null;
    }


}
