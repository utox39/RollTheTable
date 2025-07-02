package model;

import model.bean.OrdersBean;

import java.sql.SQLException;
import java.util.List;

public interface OrdersModel {
    public OrdersBean doRetriveById(int id) throws SQLException;
    public int doSave(OrdersBean ordersBean) throws SQLException;
    public List<OrdersBean> doRetrieveAll() throws SQLException;
    public List<OrdersBean> doRetrieveAllByCustomerId(int id) throws SQLException;
}
