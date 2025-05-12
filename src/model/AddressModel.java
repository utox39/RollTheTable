package model;

import model.bean.AddressBean;

import java.sql.SQLException;

public interface AddressModel {
    public AddressBean doRetriveById(int id) throws SQLException;
    public void doSave(AddressBean customerBean) throws SQLException;
}
