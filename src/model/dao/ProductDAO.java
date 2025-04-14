package model.dao;

import db.DatabaseConnectionPool;
import model.ProductModel;
import model.bean.ProductBean;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductDAO implements ProductModel {
    @Override
    public ProductBean doRetrieveById(String id) throws SQLException {
        Connection connection = null;
        ProductBean productBean = null;

        PreparedStatement preparedStatement = null;
        String sql = "SELECT product_id, name, category, purchase_price, barcode, sale_price, description, " +
                "stock_quantity, discount, minimum_age, vendor, iva, images_path, sale_status " +
                "FROM product WHERE product_id = ?";

        try {
            connection = DatabaseConnectionPool.getConnection();
            preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setString(1, id);

            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                productBean.setProductId(resultSet.getString("product_id"));
                productBean.setName(resultSet.getString("name"));
                productBean.setCategory(resultSet.getString("category"));
                productBean.setPurchasePrice(resultSet.getDouble("purchase_price"));
                productBean.setBarcode(resultSet.getString("barcode"));
                productBean.setSalePrice(resultSet.getDouble("sale_price"));
                productBean.setDescription(resultSet.getString("description"));
                productBean.setStockQuantity(resultSet.getInt("stock_quantity"));
                productBean.setDiscount(resultSet.getDouble("discount"));
                productBean.setMinimumAge(resultSet.getInt("minimum_age"));
                productBean.setVendor(resultSet.getString("vendor"));
                productBean.setIva(resultSet.getDouble("iva"));
                productBean.setImagesPath(resultSet.getString("images_path"));

                productBean.setSalesStatus(ProductBean.SalesStatus.valueOf(resultSet.getString("sale_status").toUpperCase()));
            }
        } finally {
            if (preparedStatement != null) {
                preparedStatement.close();
            }
            DatabaseConnectionPool.releaseConnection(connection);
        }

        return productBean;
    }

    @Override
    public synchronized List<ProductBean> doRetriveAll() throws SQLException {
        Connection connection = null;

        List <ProductBean> products = null;

        PreparedStatement preparedStatement = null;
        String sql = "SELECT product_id, name, category, purchase_price, barcode, sale_price, description, " +
                "stock_quantity, discount, minimum_age, vendor, iva, images_path, sale_status " +
                "FROM product";

        try {
            connection = DatabaseConnectionPool.getConnection();
            products = new ArrayList<>();

            preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                ProductBean productBean = new ProductBean();
                productBean.setProductId(resultSet.getString("product_id"));
                productBean.setName(resultSet.getString("name"));
                productBean.setCategory(resultSet.getString("category"));
                productBean.setPurchasePrice(resultSet.getDouble("purchase_price"));
                productBean.setBarcode(resultSet.getString("barcode"));
                productBean.setSalePrice(resultSet.getDouble("sale_price"));
                productBean.setDescription(resultSet.getString("description"));
                productBean.setStockQuantity(resultSet.getInt("stock_quantity"));
                productBean.setDiscount(resultSet.getDouble("discount"));
                productBean.setMinimumAge(resultSet.getInt("minimum_age"));
                productBean.setVendor(resultSet.getString("vendor"));
                productBean.setIva(resultSet.getDouble("iva"));
                productBean.setImagesPath(resultSet.getString("images_path"));

                productBean.setSalesStatus(ProductBean.SalesStatus.valueOf(resultSet.getString("sale_status").toUpperCase()));

                products.add(productBean);
            }

        } finally {
            if (preparedStatement != null) {
                preparedStatement.close();
            }
            DatabaseConnectionPool.releaseConnection(connection);
        }

        return products;
    }
}
