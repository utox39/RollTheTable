package model;

import model.bean.ProductBean;

import java.sql.SQLException;
import java.util.List;

public interface ProductModel {
    public ProductBean doRetrieveById(String id) throws SQLException;
    public List<ProductBean> doRetriveAll() throws SQLException;
}
