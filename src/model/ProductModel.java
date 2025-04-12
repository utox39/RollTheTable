package model;

import model.bean.ProductBean;

import java.sql.SQLException;
import java.util.List;

public interface ProductModel {
    public List<ProductBean> doRetriveAll() throws SQLException;
}
