package model;

import model.bean.CustomerBean;

import java.sql.SQLException;

public interface CustomerModel {
    public CustomerBean doRetriveById(int id) throws SQLException;
    public void doSave(CustomerBean customerBean) throws SQLException;
}
