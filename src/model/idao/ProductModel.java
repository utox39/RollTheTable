package model.idao;

import model.bean.ProductBean;

import java.sql.SQLException;
import java.util.List;

public interface ProductModel {
    public ProductBean doRetrieveById(String id) throws SQLException;
    public List<ProductBean> doRetriveAll() throws SQLException;
    public void doSave(ProductBean productBean) throws SQLException;
    public void doUpdate(ProductBean productBean, int id) throws SQLException;
    public void doDelete(int id) throws SQLException;
}
